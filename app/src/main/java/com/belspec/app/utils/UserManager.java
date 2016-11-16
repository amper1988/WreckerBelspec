package com.belspec.app.utils;

public class UserManager {
    public static UserManager instanse;
    private String mLogin;
    private String mPassword;
    private String mFullName;
    private boolean mRegistered;
    private int mUserType;
    private String organization;

    private UserManager() {
        this.mLogin = "";
        this.mPassword = "";
        this.mFullName = "";
        this.mUserType = 0;
        this.organization = "";
        this.mRegistered = false;
    }

    public static UserManager getInstanse(){
        if (instanse == null){
            instanse = new UserManager();
        }
        return instanse;

    };

    public void logout(){
        this.mRegistered = false;
        this.mLogin = "";
        this.mPassword = "";
        this.mFullName = "";
        this.organization = "";
        this.mUserType = 0;
    }

    public String getmFullName() {
        return mFullName;
    }

    public void setmFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public static void setInstanse(UserManager instanse) {
        UserManager.instanse = instanse;
    }

    public int getUserType() {
        return mUserType;
    }

    public void setUserType(int mUserType) {
        this.mUserType = mUserType;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setUserData(String login, String pasword, String fullName, int userType, boolean registered, String organization){
        this.mLogin = login;
        this.mPassword = pasword;
        this.mRegistered = registered;
        this.mFullName = fullName;
        this.mUserType = userType;
        this.organization = organization;




    }

    public String getmLogin() {

        return mLogin;
    }

    public void setmLogin(String mLogin) {
        this.mLogin = mLogin;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public boolean ismRegistered() {
        return mRegistered;
    }

    public void setmRegistered(boolean mRegistered) {
        this.mRegistered = mRegistered;
    }
}
