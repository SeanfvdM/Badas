package com.badas.badasstyle.FontDownloader;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.provider.FontRequest;

import androidx.annotation.ArrayRes;
import androidx.annotation.RequiresApi;

import com.badas.badasstyle.FontDownloader.FontQuery.FontQueryBuilder;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: FontTest
 * By: Seanf
 * Created: 08,September,2020
 */
public class FontRequestBuilder {
    private final Context context;
    private String providerAuthority = "com.google.android.gms.fonts",
            providerPackage = "com.google.android.gms";
    private List<List<byte[]>> certificates;
    private FontBuilderListener fontBuilderListener;
    private String query;
    private FontQueryBuilder fontQueryBuilder;

    public FontRequestBuilder(Context context, @ArrayRes int id) {
        this.context = context;
        getCertificates(id);
    }

    public FontRequestBuilder(Context context) {
        this.context = context;
    }

    public FontRequestBuilder setFontBuilderListener(FontBuilderListener fontBuilderListener) {
        this.fontBuilderListener = fontBuilderListener;
        return this;
    }

    public FontRequestBuilder getCertificates(@ArrayRes int id) {
        //https://stackoverflow.com/a/6774856
        TypedArray certsLocation = context.getResources().obtainTypedArray(id);
        certificates = new ArrayList<>();
        List<byte[]> bytes;
        int index = 0;
        try {
            while (certsLocation.getResourceId(index, 0) != -1) {
                bytes = new ArrayList<>();
                for (String item : context.getResources().getStringArray(certsLocation.getResourceId(index, 0))) {
                    bytes.add(item.getBytes());
                }
                certificates.add(bytes);
                index++;
            }
        } catch (RuntimeException ignored) {
            if (fontBuilderListener != null)
                fontBuilderListener.onFailedListener(Failed.FAILED_NOT_CERTIFICATE_ARRAYS);
            else if (index == 0)
                throw new InvalidParameterException("Invalid Certificate: The provided array is not the correct format.");
        }
        if (certificates.size() <= 0 && fontBuilderListener != null)
            fontBuilderListener.onFailedListener(Failed.FAILED_INVALID_ARRAY);
        else if (certificates.size() <= 0)
            throw new InvalidParameterException("Invalid Array: The provided array is empty.");

        certsLocation.recycle();
        return this;
    }

    public FontRequestBuilder setProviderAuthority(String providerAuthority) {
        this.providerAuthority = providerAuthority;
        return this;
    }

    public FontRequestBuilder setProviderPackage(String providerPackage) {
        this.providerPackage = providerPackage;
        return this;
    }

    public FontRequestBuilder setCertificates(List<List<byte[]>> certificates) {
        this.certificates = certificates;
        return this;
    }

    public FontRequestBuilder setFontQueryBuilder(FontQueryBuilder fontQueryBuilder) {
        this.fontQueryBuilder = fontQueryBuilder;
        return this;
    }

    public FontRequestBuilder setQuery(String query) {
        this.query = query;
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FontRequest build() {
        if (fontQueryBuilder != null) {
            if (certificates.size() > 0)
                return new FontRequest(providerAuthority, providerPackage, fontQueryBuilder.Build(), certificates);
            else
                return new FontRequest(providerAuthority, providerPackage, fontQueryBuilder.Build());
        } else {
            if (certificates.size() > 0)
                return new FontRequest(providerAuthority, providerPackage, query, certificates);
            else
                return new FontRequest(providerAuthority, providerPackage, query);
        }

    }

    public interface FontBuilderListener {
        void onFailedListener(@Failed int reason);
    }

    public @interface Failed {
        int FAILED_INVALID_ARRAY = 0;
        int FAILED_NOT_CERTIFICATE_ARRAYS = 1;
    }
}
