package com.badas.badasstyle.FontDownloader;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.FontRequest;
import android.provider.FontsContract;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.provider.FontsContractCompat;

import com.badas.badasstyle.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.koolio.library.DownloadableFontList;
import com.koolio.library.Font;
import com.koolio.library.FontList;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@TargetApi(26)
public class FontDialogFragment extends BottomSheetDialogFragment {
    private final String apiKey;
    private View view;
    private ChipGroup chipGroup;
    private AutoCompleteTextView fontSearch;
    private MaterialButton loadFont;
    private TextView sampleText;
    private FontList fontList = new FontList();
    private Font selected;
    private HandlerThread handlerThread;
    private Handler handler;
    private boolean showRelativeFS = false;
    private Typeface selectedTypeface;
    private Font selectedFont;
    private FontListener fontListener;

    public FontDialogFragment(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setFontListener(FontListener fontListener) {
        this.fontListener = fontListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.font_bottomsheet, container, false);
        init();
        return view;
    }

    private void init() {
        ((TextView) view.findViewById(R.id.attribute)).setMovementMethod(LinkMovementMethod.getInstance());

        handlerThread = new HandlerThread("fontThread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());

        Slider slider = view.findViewById(R.id.fontSize);
        if (showRelativeFS) {
            view.findViewById(R.id.tv_deviceFontData).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.tv_deviceFontData))
                    .setText(MessageFormat.format("{0}{1}{2}{3}", getString(R.string.dfs), new DecimalFormat("0.##").format(getResources().getConfiguration().fontScale), getString(R.string.rfs), new DecimalFormat("0.00 px").format(getResources().getConfiguration().fontScale * slider.getValue())));
        }
        ((TextView) view.findViewById(R.id.fontSizeText)).setText(new DecimalFormat("0.## sp").format(slider.getValue()));
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                if (value < 12) {
                    ((TextView) view.findViewById(R.id.fontSizeText)).setTextColor(getResources().getColor(R.color.design_default_color_error, null));
                } else {
                    ((TextView) view.findViewById(R.id.fontSizeText)).setTextColor(((TextView) view.findViewById(R.id.tv_deviceFontData)).getTextColors());
                }
                ((TextView) view.findViewById(R.id.fontSizeText)).setText(new DecimalFormat("0.## sp").format(value));
                ((TextView) view.findViewById(R.id.tv_deviceFontData)).setText(MessageFormat.format("{0}{1}{2}{3}", getString(R.string.dfs), new DecimalFormat("0.##").format(getResources().getConfiguration().fontScale), getString(R.string.rfs), new DecimalFormat("0.00 px").format(getResources().getConfiguration().fontScale * value)));
                sampleText.setTextSize(value);
            }
        });
        slider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0.## sp").format(value);
            }
        });
        chipGroup = view.findViewById(R.id.chipGroup);
        fontSearch = view.findViewById(R.id.actv_fontSearch);
        chipGroup.setVisibility(View.GONE);
        loadFont = view.findViewById(R.id.btn_load);
        sampleText = view.findViewById(R.id.sampleText);
        sampleText.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels / 4);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                try {
                    Chip chip = view.findViewById(checkedId);
                    for (int i = 0; i < selected.getFontVariants().length; i++) {
                        if (selected.getFontVariants()[i].equalsIgnoreCase(chip.getTag().toString())) {
                            loadFont(selected.getQueryString(i));
                            return;
                        }
                    }
                } catch (Exception ignored) {
                    sampleText.setText(R.string.sfv);
                }

            }
        });

        loadFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sampleText.setText(R.string.loading_font);
                sampleText.setTypeface(null);
                fontSearch.clearFocus();
                selected = null;
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(fontSearch.getWindowToken(), 0);
                }
                for (int i = 0; i < fontList.getFontArrayList().size(); i++) {
                    if (fontSearch.getText().toString().equalsIgnoreCase(fontList.getFontArrayList().get(i).getFontFamily())) {
                        selected = fontList.getFontArrayList().get(i);
                        loadVariantChips(selected.getFontVariants());
                        view.findViewById(R.id.btn_select).requestFocus();
                        ((MaterialButton) view.findViewById(R.id.btn_select)).setText(MessageFormat.format("{0} {1}", getString(R.string.selected_font), selected.getFontFamily()));
                        break;
                    }
                }
                view.findViewById(R.id.btn_select).setEnabled(true);
                if (selected == null) {
                    sampleText.setText(R.string.pevff);
                    selectedTypeface = null;
                    view.findViewById(R.id.btn_select).setEnabled(false);
                    ((MaterialButton) view.findViewById(R.id.btn_select)).setText(R.string.select_font_null);
                }
                selectedFont = selected;
            }
        });

        DownloadableFontList.FontListCallback fontListCallback = new DownloadableFontList.FontListCallback() {
            @Override
            public void onFontListRetrieved(FontList list) {
                fontList = list;
                fontSearch.setEnabled(true);
                loadFont.setEnabled(true);
                view.findViewById(R.id.til_fontSearch).setEnabled(true);
                fontSearch.requestFocus();
                //https://stackoverflow.com/a/7291048
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(fontSearch, InputMethodManager.SHOW_IMPLICIT);
                }
                fontSearch.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, fontList.getFontFamilyList()));
            }

            @Override
            public void onTypefaceRequestFailed(int i) {
                view.findViewById(R.id.btn_select).setEnabled(false);
            }
        };

        view.findViewById(R.id.btn_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fontListener.onFontSelectedListener(selectedFont, selectedTypeface);
                } catch (Exception ignored) {

                }
                dismissAllowingStateLoss();
            }
        });

        DownloadableFontList.requestDownloadableFontList(fontListCallback, apiKey);
    }

    public void loadVariantChips(String[] variants) {
        chipGroup.removeAllViews();
        try {
            chipGroup.clearCheck();
        } catch (Exception ignored) {

        }
        Chip child;
        for (String variant : variants) {
            child = (Chip) LayoutInflater.from(view.getContext())
                    .inflate(R.layout.font_chip, chipGroup, false);
            child.setId(variant.hashCode());
            child.setText(variant);
            child.setTag(variant);
            if (variant.contains("italic")) {
                child.setTypeface(null, Typeface.ITALIC);
                if (!variant.equals("italic"))
                    child.setText(variant.replace("italic", " italic"));
            }

            chipGroup.addView(child);
            if (variant.contains("regular"))
                chipGroup.check(variant.hashCode());
        }
        if (chipGroup.getCheckedChipId() == -1)
            chipGroup.check(variants[0].hashCode());
        chipGroup.setVisibility(View.VISIBLE);
    }

    public FontDialogFragment showRelativeFontSize() {
        showRelativeFS = true;
        try {
            view.findViewById(R.id.tv_deviceFontData).setVisibility(View.VISIBLE);
        } catch (Exception ignored) {

        }
        return this;
    }

    private void loadFont(String query) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestFont(query);
        } else {
            requestFontLegacy(query);
        }
    }

    private List<List<byte[]>> getCertificate() {
        byte[] dev = getResources().getStringArray(R.array.com_google_android_gms_fonts_certs_dev)[0].getBytes();
        byte[] prod = getResources().getStringArray(R.array.com_google_android_gms_fonts_certs_prod)[0].getBytes();
        List<byte[]> com_google_android_gms_fonts_certs_dev = new ArrayList<>();
        com_google_android_gms_fonts_certs_dev.add(dev);
        List<byte[]> com_google_android_gms_fonts_certs_prod = new ArrayList<>();
        com_google_android_gms_fonts_certs_prod.add(prod);
        List<List<byte[]>> com_google_android_gms_fonts_certs = new ArrayList<>();
        com_google_android_gms_fonts_certs.add(com_google_android_gms_fonts_certs_dev);
        com_google_android_gms_fonts_certs.add(com_google_android_gms_fonts_certs_prod);
        return com_google_android_gms_fonts_certs;
    }

    private void requestFont(String query) {
        FontRequest request;
        try {
            request = new FontRequest(
                    "com.google.android.gms.fonts",
                    "com.google.android.gms",
                    query,
                    getCertificate()
            );
        } catch (Exception ignored) {
            request = new FontRequest(
                    "com.google.android.gms.fonts",
                    "com.google.android.gms",
                    query
            );
        }

        FontsContract.FontRequestCallback callback =
                new FontsContract.FontRequestCallback() {
                    @Override
                    public void onTypefaceRetrieved(Typeface typeface) {
                        sampleText.setText(R.string.sample_text);
                        sampleText.setTypeface(typeface);
                        selectedTypeface = typeface;
                    }

                    @SuppressLint({"RestrictedApi", "SwitchIntDef"})
                    @Override
                    public void onTypefaceRequestFailed(int reason) {
                        view.findViewById(R.id.btn_select).setEnabled(false);
                        switch (reason) {
                            case FontsContract.FontRequestCallback.FAIL_REASON_FONT_LOAD_ERROR:
                                sampleText.setText(R.string.flf);
                                break;
                            case FontsContract.FontRequestCallback.FAIL_REASON_FONT_NOT_FOUND:
                                sampleText.setText(R.string.fnf);
                                break;
                            case FontsContract.FontRequestCallback.FAIL_REASON_FONT_UNAVAILABLE:
                                sampleText.setText(R.string.fu);
                                break;
                            case FontsContract.FontRequestCallback.FAIL_REASON_MALFORMED_QUERY:
                                sampleText.setText(R.string.fqm);
                                break;
                            case FontsContract.FontRequestCallback.FAIL_REASON_PROVIDER_NOT_FOUND:
                                sampleText.setText(R.string.fpnf);
                                break;
                            case FontsContract.FontRequestCallback.FAIL_REASON_WRONG_CERTIFICATES:
                                sampleText.setText(R.string.wc);
                                break;
                            default:
                                break;
                        }
                    }
                };
        FontsContract.requestFonts(requireContext(), request, handler, new CancellationSignal(), callback);
    }

    private void requestFontLegacy(String query) {
        androidx.core.provider.FontRequest request = new androidx.core.provider.FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                query,
                R.array.com_google_android_gms_fonts_certs
        );
        FontsContractCompat.FontRequestCallback callback =
                new FontsContractCompat.FontRequestCallback() {
                    @Override
                    public void onTypefaceRetrieved(Typeface typeface) {
                        sampleText.setText(R.string.sample_text);
                        sampleText.setTypeface(typeface);
                    }

                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onTypefaceRequestFailed(int reason) {
                        switch (reason) {
                            case FontsContractCompat.FontRequestCallback.FAIL_REASON_FONT_LOAD_ERROR:
                                sampleText.setText(R.string.flf);
                                break;
                            case FontsContractCompat.FontRequestCallback.FAIL_REASON_FONT_NOT_FOUND:
                                sampleText.setText(R.string.fnf);
                                break;
                            case FontsContractCompat.FontRequestCallback.FAIL_REASON_FONT_UNAVAILABLE:
                                sampleText.setText(R.string.fu);
                                break;
                            case FontsContractCompat.FontRequestCallback.FAIL_REASON_MALFORMED_QUERY:
                                sampleText.setText(R.string.fqm);
                                break;
                            case FontsContractCompat.FontRequestCallback.FAIL_REASON_PROVIDER_NOT_FOUND:
                                sampleText.setText(R.string.fpnf);
                                break;
                            case FontsContractCompat.FontRequestCallback.FAIL_REASON_SECURITY_VIOLATION:
                                sampleText.setText(R.string.sv);
                                break;
                            case FontsContractCompat.FontRequestCallback.FAIL_REASON_WRONG_CERTIFICATES:
                                sampleText.setText(R.string.wc);
                                break;
                            case FontsContractCompat.FontRequestCallback.RESULT_OK:
                                sampleText.setText(R.string.fl);
                                break;
                            default:
                                break;
                        }
                    }
                };
        FontsContractCompat.requestFont(requireContext(), request, callback, handler);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        onDestroy();
    }

    public interface FontListener {
        void onFontSelectedListener(Font lastSelectedFont, Typeface lastSelectedTypeface);
    }
}