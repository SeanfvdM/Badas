package com.badas.gamemanager;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * Project: BadasSolution
 * By: Seanf
 * Created: 14,October,2020
 */
class GameMenuItem {
    public String tag;
    public String title;
    public String extraText;
    public Bitmap image;
    public Drawable imageDrawable;
    public Color imageBackgroundColor;
    public Color imageForegroundColor;
    private MenuItemListener menuItemListener;

    public GameMenuItem(String tag, String title, String extraText, Drawable imageDrawable, Color imageBackgroundColor, MenuItemListener menuItemListener) {
        this.tag = tag;
        this.title = title;
        this.extraText = extraText;
        this.imageDrawable = imageDrawable;
        this.imageBackgroundColor = imageBackgroundColor;
        this.menuItemListener = menuItemListener;
    }

    public GameMenuItem(String tag, String title, String extraText, Drawable imageDrawable, Color imageBackgroundColor) {
        this.tag = tag;
        this.title = title;
        this.extraText = extraText;
        this.imageDrawable = imageDrawable;
        this.imageBackgroundColor = imageBackgroundColor;
    }

    public GameMenuItem(String tag, String title, String extraText, Bitmap image, Color imageBackgroundColor, MenuItemListener menuItemListener) {
        this.tag = tag;
        this.title = title;
        this.extraText = extraText;
        this.image = image;
        this.imageBackgroundColor = imageBackgroundColor;
        this.menuItemListener = menuItemListener;
    }

    public GameMenuItem(String tag, String title, String extraText, Bitmap image, Color imageBackgroundColor) {
        this.tag = tag;
        this.title = title;
        this.extraText = extraText;
        this.image = image;
        this.imageBackgroundColor = imageBackgroundColor;
    }

    public com.badas.gamemanager.GameMenuItem setImageForegroundColor(Color imageForegroundColor) {
        this.imageForegroundColor = imageForegroundColor;
        return this;
    }

    MenuItemListener getMenuItemListener() {
        return menuItemListener;
    }

    public void setMenuItemListener(MenuItemListener menuItemListener) {
        this.menuItemListener = menuItemListener;
    }
}
