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

    public FontQueryBuilder extractVariant(String variant) {
        this.italic = variant.contains("italic");
        switch (Integer.parseInt(
                variant.replace("italic", "")
                        .replace("regular", "400")
                        .replace(" ", "")
        )) {
            case Weight.WEIGHT_100:
                weight = Weight.WEIGHT_100;
                break;
            case Weight.WEIGHT_200:
                weight = Weight.WEIGHT_200;
                break;
            case Weight.WEIGHT_300:
                weight = Weight.WEIGHT_300;
                break;
            case Weight.WEIGHT_400:
                weight = Weight.WEIGHT_400;
                break;
            case Weight.WEIGHT_500:
                weight = Weight.WEIGHT_500;
                break;
            case Weight.WEIGHT_600:
                weight = Weight.WEIGHT_600;
                break;
            case Weight.WEIGHT_700:
                weight = Weight.WEIGHT_700;
                break;
            case Weight.WEIGHT_800:
                weight = Weight.WEIGHT_800;
                break;
            case Weight.WEIGHT_900:
                weight = Weight.WEIGHT_900;
                break;
        }
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
