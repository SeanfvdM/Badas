package com.badas.badasoptions;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.provider.FontRequest;
import android.provider.FontsContract;

import androidx.annotation.ColorInt;

import com.badas.badasstyle.FontDownloader.FontQuery.GoogleFontsQuery;
import com.badas.badasstyle.FontDownloader.FontRequestBuilder;
import com.badas.badasstyle.R;

import java.io.File;
import java.util.HashMap;

public class Settings {
    private static final HashMap<String, Boolean> loaded = new HashMap<>();
    static SharedPreferences sharedPreferences;
    static File fileDir;
    private static WaitingEvent waitingEvent;
    private static final WaitingEvents waitingEvents = new WaitingEvents() {
        @Override
        public void moduleLoaded() {
            int done = 0;
            for (Boolean value : loaded.values())
                if (value)
                    done++;
            if (done == loaded.values().size())
                waitingEvent.loaded();
        }
    };

    private Settings() {

    }

    public static void init(Activity activity, WaitingEvent waitingEvent) {
        loaded.put(Font.class.getSimpleName(), false);

        Settings.waitingEvent = waitingEvent;
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        fileDir = activity.getFilesDir();

        Font.getStoredFont(activity);
    }

    interface WaitingEvents {
        void moduleLoaded();
    }

    public interface WaitingEvent {
        void loaded();
    }

    public static class Font {
        static com.badas.badasstyle.FontDownloader.Font selectedFont = null;
        static Typeface typeface;
        static int size = 30;

        public static Typeface getTypeface() {
            if (selectedFont != null)
                if (selectedFont.getTypeface() != null && typeface != selectedFont.getTypeface())
                    typeface = selectedFont.getTypeface();
            return typeface;
        }

        public static void setSelectedFont(com.badas.badasstyle.FontDownloader.Font selectedFont) {
            Font.selectedFont = selectedFont;
        }

        public static void setSelectedFontSize(int size) {
            Font.size = size;
        }

        public static void storeFont(String fontVariant, int size) {
            if (sharedPreferences == null)
                throw new NullPointerException("Shared Preferences cannot be null");
            if (selectedFont == null)
                throw new NullPointerException("Cannot store null for font");
            SharedPreferences.Editor font = sharedPreferences.edit();
            font.putString("font", new GoogleFontsQuery()
                    .setFontFamily(selectedFont.getFamily())
                    .extractVariant(fontVariant).Build());
            font.putString("fontFamily", selectedFont.getFamily());
            Font.size = size;
            font.putInt("fontSize", size);
            font.apply();
        }

        public static void clearFont() {
            if (sharedPreferences == null)
                throw new NullPointerException("Shared Preferences cannot be null");
            SharedPreferences.Editor font = sharedPreferences.edit();
            font.remove("font");
            font.remove("fontFamily");
            font.remove("fontSize");
            font.apply();
        }

        public static String getFontFamily() {
            if (getTypeface() != null)
                return sharedPreferences.getString("fontFamily", "Lato");
            else
                return "Lato";
        }

        public static int getFontSize() {
            return size;
        }

        public static void getStoredFont(Context context) {
            if (sharedPreferences == null)
                throw new NullPointerException("Shared Preferences cannot be null");
            try {
                FontRequest fontRequest = new FontRequestBuilder(context, R.array.com_google_android_gms_fonts_certs)
                        .setQuery(sharedPreferences.getString("font", null)).build();
                FontsContract.requestFonts(context, fontRequest, new Handler(), null, new FontsContract.FontRequestCallback() {
                    @Override
                    public void onTypefaceRetrieved(Typeface typeface) {
                        Font.typeface = typeface;
                        loaded.put(Font.class.getSimpleName(), true);
                        waitingEvents.moduleLoaded();
                    }

                    @Override
                    public void onTypefaceRequestFailed(int reason) {
                        loaded.put(Font.class.getSimpleName(), true);
                        waitingEvents.moduleLoaded();
                    }
                });
            } catch (Exception ignored) {

            }
            size = sharedPreferences.getInt("fontSize", 30);

        }
    }

    public static class ColorCalculator {
        //https://medium.muz.li/the-science-of-color-contrast-an-expert-designers-guide-33e84c41d156
        //used for color calculations
        static double RedIntensity = 0.2126;
        static double GreenIntensity = 0.7152;
        static double BlueIntensity = 0.0722;
        static double ContrastThreshold = 0.140;

        private static double luminance(Color color) {
            if (color.red() == color.green() && color.red() == color.blue())
                return color.red();
            return RedIntensity * color.red() + GreenIntensity * color.green() + BlueIntensity * color.blue();
        }

        public static @ColorInt
        int getTextColor(Color backgroundColor) {
            return luminance(backgroundColor) < ContrastThreshold ? Color.WHITE : Color.BLACK;
        }
    }
}
