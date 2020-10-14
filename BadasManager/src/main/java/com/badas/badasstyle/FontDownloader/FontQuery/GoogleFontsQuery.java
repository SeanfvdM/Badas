package com.badas.badasstyle.FontDownloader.FontQuery;

import android.text.TextUtils;

/**
 * Project: FontTest
 * By: Seanf
 * Created: 09,September,2020
 */
@SuppressWarnings("unused")
public class GoogleFontsQuery extends FontQueryBuilder {

    public GoogleFontsQuery() {
    }

    public GoogleFontsQuery(String fontFamily) {
        super(fontFamily);
    }

    public GoogleFontsQuery(int weight) {
        super(weight);
    }

    public GoogleFontsQuery(boolean italic) {
        super(italic);
    }

    public GoogleFontsQuery(String fontFamily, int weight) {
        super(fontFamily, weight);
    }

    public GoogleFontsQuery(String fontFamily, boolean italic) {
        super(fontFamily, italic);
    }

    public GoogleFontsQuery(int weight, boolean italic) {
        super(weight, italic);
    }

    public GoogleFontsQuery(String fontFamily, int weight, boolean italic) {
        super(fontFamily, weight, italic);
    }

    @Override
    public String Build() {
        if (fontFamily.isEmpty() || TextUtils.isEmpty(fontFamily) || fontFamily == null) {
            throw new NullPointerException("Font Family cannot be null or empty");
        } else {
            if (!italic && (weight == Weight.WEIGHT_UNSPECIFIED || weight == Weight.WEIGHT_REGULAR)) {
                return fontFamily;
            } else {
                if (italic && (weight == Weight.WEIGHT_UNSPECIFIED || weight == Weight.WEIGHT_REGULAR)) {
                    return "name=" + fontFamily + "&italic=1";
                } else if (weight > Weight.WEIGHT_UNSPECIFIED && !italic) {
                    return "name=" + fontFamily + "&weight=" + weight;
                } else {
                    return "name=" + fontFamily + "&weight=" + weight + "&italic=1";
                }
            }
        }
    }
}
