package com.badas.badasoptions;

public class General {
    private static General instance;
    private final Class<?> userActivity, childActivity, loginActivity;
    private final boolean isDemo = false;

    public General(Class<?> userActivity, Class<?> childActivity, Class<?> loginActivity) {
        this.userActivity = userActivity;
        this.childActivity = childActivity;
        this.loginActivity = loginActivity;
        instance = this;
    }

    public static General getInstance() {
        return instance;
    }

    public boolean isDemo() {
        return isDemo;
    }

    public Class<?> getUserActivity() {
        return userActivity;
    }

    public Class<?> getChildActivity() {
        return childActivity;
    }

    public Class<?> getLoginActivity() {
        return loginActivity;
    }
}
