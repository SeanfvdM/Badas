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

import com.badas.badasoptions.Settings;
import com.badas.badassolution.BuildConfig;
import com.badas.badassolution.R;
import com.badas.badasstyle.FontDownloader.Font;
import com.badas.badasstyle.FontDownloader.FontBottomDialogFragment;
import com.badas.badasstyle.FontDownloader.FontDialogFragment;
import com.google.firebase.auth.FirebaseAuth;

public class PlaceholderFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_placeholder, container, false);
        final TextView textView = root.findViewById(R.id.text_placeholder);
        textView.setText("This is a placeholder screen");

        final FontBottomDialogFragment fontBottomDialogFragment =
                new FontBottomDialogFragment(BuildConfig.GoogleFont_Key)
                        .showRelativeFontSize();
        fontBottomDialogFragment.setFontListener(new FontBottomDialogFragment.FontListener() {
            @Override
            public void onFontSelectedListener(String lastSelectedFontVariant, Font lastSelectedFont, Typeface lastSelectedTypeface, int size) {
                Toast.makeText(requireContext(), lastSelectedFont.getFamily(), Toast.LENGTH_SHORT).show();

                Settings.Font.setSelectedFont(lastSelectedFont);
                Settings.Font.storeFont(lastSelectedFontVariant, size);
            }
        });

        Button button2 = root.findViewById(R.id.font_button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontBottomDialogFragment.show(getChildFragmentManager(), "Fonts");
            }
        });

        Button button = root.findViewById(R.id.font_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FontDialogFragment.display(getChildFragmentManager());
            }
        });

        root.findViewById(R.id.btn_signOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });
        return root;
    }
}