package com.badas.badassolution.ui.placeholder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.badas.badassolution.R;

public class PlaceholderFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        @SuppressWarnings("deprecation") PlaceholderViewModel placeholderViewModel = ViewModelProviders.of(this).get(PlaceholderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_placeholder, container, false);
        final TextView textView = root.findViewById(R.id.text_placeholder);
        placeholderViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}