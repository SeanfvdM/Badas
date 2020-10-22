package com.badas.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * Project: BadasSolution
 * By: Seanf
 * Created: 22,October,2020
 */
class ResetPasswordSheet extends BottomSheetDialogFragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(com.badas.badasstyle.R.layout.font_bottomsheet, container, false);
        return view;
    }
}
