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

import com.badas.badassolution.BuildConfig;
import com.badas.badassolution.R;
import com.badas.badasstyle.FontDownloader.Font;
import com.badas.badasstyle.FontDownloader.FontDialogFragment;

public class PlaceholderFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_placeholder, container, false);
        final TextView textView = root.findViewById(R.id.text_placeholder);
        textView.setText("This is a placeholder screen");

        final FontDialogFragment fontDialogFragment = new FontDialogFragment(BuildConfig.GoogleFont_Key);
        fontDialogFragment.showRelativeFontSize();
        fontDialogFragment.setFontListener(new FontDialogFragment.FontListener() {
            @Override
            public void onFontSelectedListener(Font lastSelectedFont, Typeface lastSelectedTypeface) {
                Toast.makeText(requireContext(), lastSelectedFont.getFamily(), Toast.LENGTH_SHORT).show();
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

        root.findViewById(R.id.btn_signOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });
        return root;
    }
}