package com.badas.badasstyle.FontDownloader;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: FontTest
 * By: Seanf
 * Created: 09,September,2020
 */
public class FontDownloader {

    private static @FontJsonLabels
    String[] jsonLabels = new String[0];

    public FontDownloader setJsonLabels(String[] jsonLabels) {
        FontDownloader.jsonLabels = jsonLabels;
        return this;
    }

    public void requestListDownload(String apiLink, FontListDownloaderCallback callback) {
        new AsyncDownloader(callback, apiLink).execute();
    }

    public void requestListDownload(@ApiLinks String apiLink, String apiKey, FontListDownloaderCallback callback) {
        new AsyncDownloader(callback, apiLink, apiKey).execute();
    }

    public @interface ApiLinks {
        String GOOGLE_FONTS_API_NO_KEY = "https://www.googleapis.com/webfonts/v1/webfonts?key=";
    }

    public @interface FontJsonLabels {
        String[] GOOGLE_FONT_JSON_LABELS = new String[]{
                "family",
                "category",
                "variants",
                "subsets",
                "version",
                "lastModified",
                "files"
        };
    }

    public interface FontListDownloaderCallback {
        void onFontsReceived(String raw);

        void onFontsReceived(List<Font> fonts);

        void onFailed(Exception e);
    }

    private static class AsyncDownloader extends AsyncTask<Void, Void, String> {
        String ApiLink;
        FontListDownloaderCallback downloaderCallback;

        public AsyncDownloader(FontListDownloaderCallback downloaderCallback, String apiLink) {
            ApiLink = apiLink;
            this.downloaderCallback = downloaderCallback;
        }

        public AsyncDownloader(FontListDownloaderCallback downloaderCallback, @ApiLinks String apiLink, String apiKey) {
            ApiLink = apiLink + apiKey;
            this.downloaderCallback = downloaderCallback;
        }

        @Override
        protected String doInBackground(Void... voids) {
            BufferedReader reader;
            try {
                URL url = new URL(ApiLink);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder buffer = new StringBuilder();
                char[] chars = new char[buffer.capacity()];
                int read;
                while ((read = reader.read(chars)) != -1) {
                    buffer.append(chars, 0, read);
                }

                return buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                downloaderCallback.onFailed(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            downloaderCallback.onFontsReceived(result);
            downloaderCallback.onFontsReceived(parseJson(result));
        }

        private List<Font> parseJson(String raw) {
            //todo continue here
            return new ArrayList<>();
        }
    }
}
