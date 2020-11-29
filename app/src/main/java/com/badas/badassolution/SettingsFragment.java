package com.badas.badassolution;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.badas.badasoptions.Settings;
import com.badas.badasstyle.FontDownloader.Font;
import com.badas.badasstyle.FontDownloader.FontBottomDialogFragment;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        final Preference fontPref = (Preference) findPreference("font");

        fontPref.setSummary("Font: " + Settings.Font.getFontFamily() + " Size: " + Settings.Font.getFontSize() + "sp");

        final FontBottomDialogFragment fontBottomDialogFragment =
                new FontBottomDialogFragment(BuildConfig.GoogleFont_Key);
        fontBottomDialogFragment.setFontListener(new FontBottomDialogFragment.FontListener() {
            @Override
            public void onFontSelectedListener(String lastSelectedFontVariant, Font lastSelectedFont, Typeface lastSelectedTypeface, int size) {
                Settings.Font.setSelectedFont(lastSelectedFont);
                Settings.Font.storeFont(lastSelectedFontVariant, size);
                fontPref.setSummary("Font: " + Settings.Font.getFontFamily() + " Size: " + Settings.Font.getFontSize() + "sp");
            }
        });

        fontPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                fontBottomDialogFragment.show(getChildFragmentManager(), "Fonts");
                return true;
            }
        });
    }
}