package com.badas.badassolution.ChildScreen.Games;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.badas.badasoptions.Settings;
import com.badas.badassolution.R;

public class GameSelectorFragmentTemplate extends Fragment {
    private String mTitle = "NA";
    private String mDescription = "";
    private @DrawableRes
    int mIcon = -1;
    private @ColorInt
    int mColor = -1;

    public GameSelectorFragmentTemplate() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_selector_template, container, false);
        TextView title = view.findViewById(R.id.tv_Title);
        title.setText(mTitle);
        title.setTypeface(Settings.Font.getTypeface());
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, Settings.Font.getFontSize());
        TextView description = view.findViewById(R.id.tv_Description);
        description.setText(mDescription);
        description.setTypeface(Settings.Font.getTypeface());
        description.setTextSize(TypedValue.COMPLEX_UNIT_SP, Settings.Font.getFontSize());
        if (mIcon != -1)
            ((ImageView) view.findViewById(R.id.iv_Icon)).setImageResource(mIcon);
        if (mColor != -1) {
            ((CardView) view.findViewById(R.id.cv_Card)).setCardBackgroundColor(mColor);
            Color color = Color.valueOf(((CardView) view.findViewById(R.id.cv_Card)).getCardBackgroundColor().getDefaultColor());
            title.setTextColor(Settings.ColorCalculator.getTextColor(color));
            description.setTextColor(Settings.ColorCalculator.getTextColor(color));
        }
        return view;
    }

    public GameSelectorFragmentTemplate setTitle(String Title) {
        this.mTitle = Title;
        return this;
    }

    public GameSelectorFragmentTemplate setDescription(String Description) {
        this.mDescription = Description;
        return this;
    }

    public GameSelectorFragmentTemplate setIcon(@DrawableRes int Icon) {
        this.mIcon = Icon;
        return this;
    }

    public GameSelectorFragmentTemplate setColor(@ColorInt int Color) {
        this.mColor = Color;
        return this;
    }
}