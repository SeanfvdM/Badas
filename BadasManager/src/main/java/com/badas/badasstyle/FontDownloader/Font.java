package com.badas.badasstyle.FontDownloader;

import android.graphics.Typeface;

import java.util.Date;

/**
 * Project: FontTest
 * By: Seanf
 * Created: 08,September,2020
 */
public class Font {
    String kind;
    String family;
    String[] subset = new String[0];
    String[] variants = new String[0];
    String version;
    Date lastModified;
    String[] files = new String[0];
    String category;
    Typeface typeface;
    boolean available = true;

    Font() {
    }

    public String getKind() {
        return kind;
    }

    void setKind(String kind) {
        this.kind = kind;
    }

    public String getFamily() {
        return family;
    }

    void setFamily(String family) {
        this.family = family;
    }

    public String[] getSubset() {
        return subset;
    }

    void setSubset(String[] subset) {
        this.subset = subset;
    }

    public String[] getVariants() {
        return variants;
    }

    void setVariants(String[] variants) {
        this.variants = variants;
    }

    public String getVersion() {
        return version;
    }

    void setVersion(String version) {
        this.version = version;
    }

    public Date getLastModified() {
        return lastModified;
    }

    void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String[] getFiles() {
        return files;
    }

    void setFiles(String[] files) {
        this.files = files;
    }

    public String getCategory() {
        return category;
    }

    void setCategory(String category) {
        this.category = category;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public boolean isAvailable() {
        return available;
    }

    void setAvailable(boolean available) {
        this.available = available;
    }
}
