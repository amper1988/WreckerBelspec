package com.belspec.app.retrofit;

import android.util.Base64;
import android.util.Log;

import com.belspec.app.BuildConfig;
import com.belspec.app.retrofit.aisDrive.AisDriveService;
import com.belspec.app.utils.Encode;
import com.belspec.app.utils.UserManager;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Api {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String POLICE_URL = "http://185.66.69.93:10101/stojanka/";
    private static final String AIS_DRIVE_URL = "https://test.aisdrive.by/";
    private static final String AIS_KEY = "aksdjlkjslkdjklajdAAKJSD";
    private static final String AIS_CRYPTO_KEY = "aSn8LmyNnC3ZJktFpmEmXz5K";


    public static  RetrofitService createRetrofitService() {
        LoggingInterceptor interceptor = new LoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(3600, TimeUnit.SECONDS)
                .writeTimeout(3600, TimeUnit.SECONDS)
                .connectTimeout(3600,  TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(interceptor);
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(POLICE_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        return restAdapter.create(com.belspec.app.retrofit.RetrofitService.class);
    }

    public static AisDriveService createAisDriveService(){

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120,  TimeUnit.SECONDS)
                .addInterceptor(new DefaultInterceptor());

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(AIS_DRIVE_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return restAdapter.create(AisDriveService.class);
    }

    public static Response customIntercept(Interceptor.Chain chain) throws IOException {

        RequestBody requestBody = chain.request().body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage = "--> " + chain.request().method() + ' ' + chain.request().url() + ' ' + protocol;
        Log.d("SOAP", requestStartMessage);


        long startNs = System.nanoTime();
        Response response = chain.proceed(chain.request());
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        Log.d("SOAP", "<-- " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms" + (!true ? ", "
                + bodySize + " body" : "") + ')');

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {

                return response;
            }
        }

        if (contentLength != 0) {
            Log.d("SOAP", "");
            String msg = buffer.clone().readString(charset);
            Log.d("SOAP", msg.replace("\\n", "").replace("\\r", "").replace("\\t", ""));
        }


        return response;
    }

    public static class LoggingInterceptor implements Interceptor {

        private HttpLoggingInterceptor mInterceptor;

        public LoggingInterceptor() {
            mInterceptor = new HttpLoggingInterceptor();
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            return customIntercept(chain);
        }

        public void setLevel(HttpLoggingInterceptor.Level level) {
            mInterceptor.setLevel(level);
        }
    }

    private static class DefaultInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Key", AIS_KEY)
                    .addHeader("App", BuildConfig.APPLICATION_ID)
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Authorization", getEncryptedLogin())
                    .method(original.method(), original.body());
            return chain.proceed(requestBuilder.build());
        }
    }

    private static String getEncryptedLogin(){
        return Base64.encodeToString(UserManager.getInstanse().getmLogin().getBytes(), Base64.NO_WRAP );
    }
}



