package com.badas.profilemanager;

/**
 * Project: ProfileManager
 * By: Avremi
 * Reviewed By: Seanf
 * Created: 08,September,2020
 */
public class Profile {
    /*
    todo - read me
    Feel free to rename everything to follow your naming conventions
    If you are going to rename things use Shift + F6 -> rename -> Enter
     */
    private int imageResource;//todo change to a Bitmap
    //todo add a user type i.e. String userType;
    private String name;
    private String userInfo;

    public Profile(int imageResource, String name, String userInfo) {
        this.imageResource = imageResource;
        this.name = name;
        this.userInfo = userInfo;
    }

    //todo change to bitmap
    public int getImageResource() {
        return imageResource;
    }

    //todo change to bitmap
    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
