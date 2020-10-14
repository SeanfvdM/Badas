package com.badas.badasstyle.FontDownloader.FontQuery;

/**
 * Project: FontTest
 * By: Seanf
 * Created: 09,September,2020
 */

@SuppressWarnings("unused")
public abstract class FontQueryBuilder {
    protected String fontFamily;
    protected @Weight
    int weight = Weight.WEIGHT_UNSPECIFIED;
    protected boolean italic = false;

    public FontQueryBuilder() {
    }

    public FontQueryBuilder(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public FontQueryBuilder(int weight) {
        this.weight = weight;
    }

    public FontQueryBuilder(boolean italic) {
        this.italic = italic;
    }

    public FontQueryBuilder(String fontFamily, int weight) {
        this.fontFamily = fontFamily;
        this.weight = weight;
    }

    public FontQueryBuilder(String fontFamily, boolean italic) {
        this.fontFamily = fontFamily;
        this.italic = italic;
    }

    public FontQueryBuilder(int weight, boolean italic) {
        this.weight = weight;
        this.italic = italic;
    }

    public FontQueryBuilder(String fontFamily, int weight, boolean italic) {
        this.fontFamily = fontFamily;
        this.weight = weight;
        this.italic = italic;
    }

    public FontQueryBuilder setWeight(@Weight int weight) {
        this.weight = weight;
        return this;
    }

    public FontQueryBuilder setItalic(boolean italic) {
        this.italic = italic;
        return this;
    }

    public FontQueryBuilder setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
        return this;
    }

    public abstract String Build();

    public @interface Weight {
        int WEIGHT_UNSPECIFIED = 0;
        int WEIGHT_100 = 100;
        int WEIGHT_THIN = 100;
        int WEIGHT_200 = 200;
        int WEIGHT_300 = 300;
        int WEIGHT_LIGHT = 300;
        int WEIGHT_400 = 400;
        int WEIGHT_REGULAR = 400;
        int WEIGHT_500 = 500;
        int WEIGHT_MEDIUM = 500;
        int WEIGHT_600 = 600;
        int WEIGHT_700 = 700;
        int WEIGHT_BOLD = 700;
        int WEIGHT_800 = 800;
        int WEIGHT_900 = 900;
        int WEIGHT_BLACK = 900;
    }
}
