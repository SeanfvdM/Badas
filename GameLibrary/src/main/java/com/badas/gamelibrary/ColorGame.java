package com.badas.gamelibrary;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.badas.gamelibrary.GameFragmentTemplate.GameView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Project: BadasSolution
 * By: Seanf
 * Created: 28,October,2020
 */
public class ColorGame extends GameView {
    ImageView colorDisplay;
    List<ColorData> colorList = new ArrayList<>();
    private ColorData correctColor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.color_layout, container, false);
        colorDisplay = view.findViewById(R.id.colorDisplay);
        return view;
    }

    private void setCorrectColor() {
        //the range of the random
        float min = 0, max = 100;
        //the percentile of each index i.e.
        //for 4 values with a range of 0-100:
        //0-24 = 0, 25-49 = 1, 50-74 = 2, 75-100 = 3
        float percentile = (max - min) / colorList.size();
        float random = min + new Random().nextFloat() * (max - min);
        int counter = 1;

        while (counter <= colorList.size()) {
            if (percentile * counter > random) {
                if (correctColor != colorList.get(counter - 1)) {
                    correctColor = colorList.get(counter - 1);
                    break;
                } else
                    setCorrectColor();
            }
            counter++;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        currentLevel = 0;
        updateGame();
    }

    private void updateGame() {
        setCorrectColor();
        colorDisplay.setBackgroundColor(correctColor.getColorValue());
        currentLevel++;
        pointsUpdated.onLevelChanged(currentLevel);
    }

    @Override
    GameAdapter<?> getGameAdapter() {
        colorList.clear();
        //todo swap the order or the colors
        colorList.add(new ColorData(Color.valueOf(Color.RED), "Red"));
        colorList.add(new ColorData(Color.valueOf(Color.GREEN), "Green"));
        colorList.add(new ColorData(Color.valueOf(Color.BLUE), "Blue"));
        colorList.add(new ColorData(Color.valueOf(Color.YELLOW), "Yellow"));

        importantEvents = new ImportantEvents() {
            @Override
            public void onButtonBind(MaterialButton button, int position) {
                button.setText(colorList.get(position).getColorText());
                button.setBackgroundColor(colorList.get(position).getColorValue());
                button.setTextColor(colorList.get(position).getForegroundColorValue());
                button.setRippleColor(new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_pressed},
                        },
                        new int[]{
                                colorList.get(position).getRippleColor(),
                        }
                ));

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (colorList.get(position).equals(correctColor)) {
                            Toast.makeText(requireContext(), "Correct", Toast.LENGTH_SHORT).show();
                            currentPoints++;
                            pointsUpdated.onPointsChanged(currentPoints);
                            updateGame();
                        }
                    }
                });
            }
        };
        GameAdapter<ColorData> colorGameAdapter = new GameAdapter<>();
        colorGameAdapter.setDataSet(colorList);
        return colorGameAdapter;
    }

    @Override
    GameView init() {
        setUI(true, true);
        currentLevel = 1;
        currentPoints = 0;
        return this;
    }

    static class ColorData {
        Color color = Color.valueOf(Color.BLACK);
        String colorText = "Black";

        public ColorData(Color color, String colorText) {
            this.color = color;
            this.colorText = colorText;
        }

        Color getForegroundColor() {
            return Color.valueOf(getForegroundColorValue());
        }

        int getForegroundColorValue() {
            double luminance = (0.2126 * color.red()
                    + 0.7152 * color.green()
                    + 0.0722 * color.blue());
            return (luminance < 0.140) ? Color.WHITE : Color.BLACK;
        }

        int getRippleColor() {
            return Color.valueOf(getForegroundColor().luminance(), getForegroundColor().red(), getForegroundColor().green(), getForegroundColor().blue()).toArgb();
        }

        public Color getColor() {
            return color;
        }

        public int getColorValue() {
            return color.toArgb();
        }

        public String getColorText() {
            return colorText;
        }
    }
}
