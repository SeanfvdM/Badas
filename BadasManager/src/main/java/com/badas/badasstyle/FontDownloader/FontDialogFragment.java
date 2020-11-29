package com.badas.badasstyle.FontDownloader;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.badas.badasstyle.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Project: BadasSolution
 * By: Seanf
 * Created: 17,October,2020
 */
public class FontDialogFragment extends DialogFragment {
    private Toolbar toolbar;
    private SwitchMaterial sm_customFontSize;

    public static FontDialogFragment display(FragmentManager fragmentManager) {
        FontDialogFragment fontDialogFragment = new FontDialogFragment();
        fontDialogFragment.show(fragmentManager, "FontDialogFragment");
        return fontDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BadasTheme_FullScreenDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.font_dialog, container, false);
        toolbar = view.findViewById(R.id.toolbar);

        sm_customFontSize = view.findViewById(R.id.sm_customFontSize);
        final Slider sldr_FontSize = view.findViewById(R.id.sldr_customeFontSize);

        sm_customFontSize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    view.findViewById(R.id.cl_scale).setVisibility(View.VISIBLE);
                else
                    view.findViewById(R.id.cl_scale).setVisibility(View.GONE);
                view.findViewById(R.id.tv_legible).setVisibility(View.GONE);
                sldr_FontSize.setValue(14);
            }
        });

        sldr_FontSize.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                if (sm_customFontSize.isChecked())
                    if (value * (getResources().getConfiguration().fontScale) <= 12 && value < 14) {
                        view.findViewById(R.id.tv_legible).setVisibility(View.VISIBLE);
                        ((TextView) view.findViewById(R.id.tv_legible)).setText(getString(R.string.legible_small));
                    } else if (value * (getResources().getConfiguration().fontScale) >= 60) {
                        view.findViewById(R.id.tv_legible).setVisibility(View.VISIBLE);
                        ((TextView) view.findViewById(R.id.tv_legible)).setText(R.string.legible_large);
                    } else {
                        view.findViewById(R.id.tv_legible).setVisibility(View.GONE);
                    }
                ((TextView) view.findViewById(R.id.tv_scale)).setTextSize(value);
            }
        });
        sldr_FontSize.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0.## sp").format(value);
            }
        });

        MaterialButton btn_loadFont = view.findViewById(R.id.btn_fontLoad);

        btn_loadFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.cl_preview).setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.BadasTheme_Slide);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        toolbar.setTitle("Font Modifier");
//        toolbar.inflateMenu(R.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dismiss();
                return true;
            }
        });
    }
}
