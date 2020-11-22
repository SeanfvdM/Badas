package com.badas.badassolution.ChildScreen.Games;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.badas.badassolution.R;

public class GameSelectorFragmentTemplate extends Fragment {
    private static final String ARG_TITLE = "title";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_ICON = "icon";
    private static final String ARG_COLOR = "color";

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_selector_template, container, false);
        ((TextView) view.findViewById(R.id.tv_Title)).setText(mTitle);
        ((TextView) view.findViewById(R.id.tv_Description)).setText(mDescription);
        if (mIcon != -1)
            ((ImageView) view.findViewById(R.id.iv_Icon)).setImageResource(mIcon);
        if (mColor != -1) {
            ((CardView) view.findViewById(R.id.cv_Card)).setCardBackgroundColor(mColor);
            Color color = Color.valueOf(((CardView) view.findViewById(R.id.cv_Card)).getCardBackgroundColor().getDefaultColor());
            double luminance = (0.2126 * color.red()
                    + 0.7152 * color.green()
                    + 0.0722 * color.blue());
            ((TextView) view.findViewById(R.id.tv_Title)).setTextColor((luminance < 0.140) ? Color.WHITE : Color.BLACK);
            ((TextView) view.findViewById(R.id.tv_Description)).setTextColor((luminance < 0.140) ? Color.WHITE : Color.BLACK);
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