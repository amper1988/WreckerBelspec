package com.belspec.app.gps;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;

import com.belspec.app.R;
import com.belspec.app.utils.AppHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GPSTracker extends Service implements LocationListener {

    public static final String PROVIDER_DISABLED = "PROVIDER_DISABLED";
    public static final String PROVIDER_ENABLED = "PROVIDER_ENABLED";
    public static final String LOCATION_CHANGED = "LOCATION_CHANGED";
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 1; // 1 seconds
    private static GPSTracker instance;
    // Declaring a Location Manager
    protected LocationManager locationManager;
    // Flag for GPS status
    boolean isGPSEnabled = false;
    // Flag for network status
    boolean isNetworkEnabled = false;
    Location locationGPS; // LocationGPS
    long gpsTime;
    Location locationNetwork; //LocationNetwork
    long networkTime;
    Location finalLocation;
    double latitude; // Latitude
    double longitude; // Longitude
    List<LocationDataChangeListener> locationDataChangeListenerList;

    private GPSTracker() {
        locationDataChangeListenerList = new ArrayList<>();
        locationManager = (LocationManager) AppHolder.getInstance().getContext()
                .getSystemService(LOCATION_SERVICE);
    }

    public static synchronized GPSTracker getInstance() {
        if (instance == null)
            instance = new GPSTracker();
        return instance;
    }

    public void setDataChangeListener(LocationDataChangeListener listener){
        if(listener != null && !locationDataChangeListenerList.contains(listener))
            locationDataChangeListenerList.add(listener);
    }

    public void unsetDataChangeListener(LocationDataChangeListener listener){
        if(listener != null)
            locationDataChangeListenerList.remove(listener);
    }

    public boolean startUsingGPS() {
        try {
            if(canGetLocation()){
                // If GPS enabled, get latitude/longitude using GPS Services
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        locationGPS = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        locationNetwork = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app.
     */
    private void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopUsingGPS();
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        return latitude;
    }


    /**
     * Function to get longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Function to check GPS/Wi-Fi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        // Getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return (isGPSEnabled || isNetworkEnabled);
    }


    /**
     * Function to show settings alert dialog.
     * On pressing the Settings button it will launch Settings Options.
     */

    private void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(AppHolder.getInstance().getContext(), R.style.MyMaterialTheme));

        // Setting Dialog Title
        alertDialog.setTitle("Настойте GPS");

        // Setting Dialog Message
        alertDialog.setMessage("Определение GPS-координат отключено. Хотите перейти в настройки?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Настройки", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                AppHolder.getInstance().getContext().startActivity(intent);
            }
        });
        // On pressing the cancel button
        alertDialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();

    }

    public boolean setGpsToFile(String path) {
        if (canGetLocation()) {
            double latitude = getLatitude();
            double longitude = getLongitude();
            ExifInterface exif;
            try {
                exif = new ExifInterface(path);
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, Gps.convert(latitude));
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, Gps.latitudeRef(latitude));
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, Gps.convert(longitude));
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, Gps.longitudeRef(longitude));
                exif.saveAttributes();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            this.showSettingsAlert();
            return false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location.getAccuracy() < 20) {
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                locationGPS = location;
                gpsTime = location.getTime();
            } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
                locationNetwork = location;
                networkTime = location.getTime();
            }
            Location bestLocation = null;
            if (locationGPS != null && gpsTime > networkTime)
                bestLocation = locationGPS;
            if (bestLocation == null && networkTime > gpsTime && Math.abs(networkTime - gpsTime) > 20000) {
                if (locationNetwork != null)
                    bestLocation = locationNetwork;
            }
            if (bestLocation != null) {
                latitude = bestLocation.getLatitude();
                longitude = bestLocation.getLongitude();
            }
            for (LocationDataChangeListener item : locationDataChangeListenerList) {
                item.onLocationDataChange(LOCATION_CHANGED, bestLocation);
            }
        }
        Log.d("OnLocCh", location.getProvider() + " " + location.getLatitude() + " " + location.getLongitude());
    }


    @Override
    public void onProviderDisabled(String provider) {
        if(provider.equals(LocationManager.GPS_PROVIDER))
            isGPSEnabled = false;
        if(provider.equals(LocationManager.NETWORK_PROVIDER))
            isNetworkEnabled = false;
        for(LocationDataChangeListener item : locationDataChangeListenerList){
            item.onLocationDataChange(PROVIDER_DISABLED, null);
        }
    }


    @Override
    public void onProviderEnabled(String provider) {
        if(provider.equals(LocationManager.GPS_PROVIDER))
            isGPSEnabled = true;
        if(provider.equals(LocationManager.NETWORK_PROVIDER))
            isNetworkEnabled = true;
        for(LocationDataChangeListener item : locationDataChangeListenerList){
            item.onLocationDataChange(PROVIDER_ENABLED, null);
        }
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public interface LocationDataChangeListener{
        void onLocationDataChange(String locationAction, Location location);

    }
}
