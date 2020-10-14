package com.badas.badassolution.ui.placeholder;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.badas.badassolution.R;
import com.badas.badasstyle.FontDownloader.Font;
import com.badas.badasstyle.FontDownloader.FontDialogFragment;
import com.badas.badasstyle.FontDownloader.FontDownloader;

import java.util.List;

public class PlaceholderFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_placeholder, container, false);
        final TextView textView = root.findViewById(R.id.text_placeholder);
        textView.setText("This is a placeholder screen");

        String apiKey = getString(R.string.fontKey);
        new FontDownloader()
                .setJsonLabels(FontDownloader.FontJsonLabels.GOOGLE_FONT_JSON_LABELS)
                .requestListDownload(FontDownloader.ApiLinks.GOOGLE_FONTS_API_NO_KEY,
                        apiKey,
                        new FontDownloader.FontListDownloaderCallback() {
                            @Override
                            public void onFontsReceived(String result) {

                            }

                            @Override
                            public void onFontsReceived(List<Font> fonts) {

                            }

                            @Override
                            public void onFailed(Exception e) {

                            }
                        });

        final FontDialogFragment fontDialogFragment = new FontDialogFragment(apiKey);
        fontDialogFragment.showRelativeFontSize();
        fontDialogFragment.setFontListener(new FontDialogFragment.FontListener() {
            @Override
            public void onFontSelectedListener(com.koolio.library.Font lastSelectedFont, Typeface lastSelectedTypeface) {
                Toast.makeText(requireContext(), lastSelectedFont.getFontFamily(), Toast.LENGTH_SHORT).show();
            }
        });

        Button button = root.findViewById(R.id.font_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontDialogFragment
                        .show(getChildFragmentManager(), "Fonts");
            }
        });
        return root;
    }
}