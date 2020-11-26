package com.badas.badasstyle.FontDownloader;

import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Project: FontTest
 * By: Seanf
 * Created: 08,September,2020
 */
public class Font {
    private String kind;
    private String family;
    private String[] subset = new String[0];
    private String[] variants = new String[0];
    private String version;
    private Date lastModified;
    private HashMap<String, String> files = new HashMap<>();
    private String category;
    private Typeface typeface;
    private boolean available = true;

    public String getFile(String fileKey) {
        return files.get(fileKey);
    }

    Font() {
    }

    public String getKind() {
        return kind;
    }

    public static List<String> getFontFamilies(List<Font> fonts) {
        ArrayList<String> families = new ArrayList<>();
        for (Font font : fonts)
            if (!families.contains(font.getFamily())) families.add(font.getFamily());
        return families;
    }

    public String getFamily() {
        return family;
    }

    public Font setKind(String kind) {
        this.kind = kind;
        return this;
    }

    public String[] getSubset() {
        return subset;
    }

    public Font setFamily(String family) {
        this.family = family;
        return this;
    }

    public String[] getVariants() {
        return variants;
    }

    public Font setSubset(String[] subset) {
        this.subset = subset;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public Font setVariants(String[] variants) {
        this.variants = variants;
        return this;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public Font setVersion(String version) {
        this.version = version;
        return this;
    }

    public HashMap<String, String> getFiles() {
        return files;
    }

    public Font setLastModified(Date lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Font setFiles(HashMap<String, String> files) {
        this.files = files;
        return this;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public Font setCategory(String category) {
        this.category = category;
        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public Font setTypeface(Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    public Font setAvailable(boolean available) {
        this.available = available;
        return this;
    }
}
