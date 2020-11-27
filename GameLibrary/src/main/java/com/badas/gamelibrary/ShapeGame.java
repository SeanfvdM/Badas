package com.badas.gamelibrary;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.badas.badasoptions.Settings;
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
public class ShapeGame extends GameView {
    ImageView display;
    List<ShapeData> shapeList = new ArrayList<>();
    private ShapeData correctColor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shape_layout, container, false);
        display = view.findViewById(R.id.shapeDisplay);
        return view;
    }

    private void setCorrectColor() {
        //the range of the random
        float min = 0, max = 100;
        //the percentile of each index i.e.
        //for 4 values with a range of 0-100:
        //0-24 = 0, 25-49 = 1, 50-74 = 2, 75-100 = 3
        float percentile = (max - min) / shapeList.size();
        float random = min + new Random().nextFloat() * (max - min);
        int counter = 1;

        while (counter <= shapeList.size()) {
            if (percentile * counter > random) {
                if (correctColor != shapeList.get(counter - 1)) {
                    correctColor = shapeList.get(counter - 1);
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
        display.setImageResource(correctColor.getShape());
        currentLevel++;
        pointsUpdated.onLevelChanged(currentLevel);
    }

    @Override
    GameAdapter<?> getGameAdapter() {
        shapeList.clear();
        //todo swap the order or the colors
        shapeList.add(new ShapeData(R.drawable.ic_rectangle, Color.valueOf(Color.BLUE), "Square"));
        shapeList.add(new ShapeData(R.drawable.ic_triangle, Color.valueOf(Color.YELLOW), "Triangle"));
        shapeList.add(new ShapeData(R.drawable.ic_circle, Color.valueOf(Color.RED), "Circle"));
        shapeList.add(new ShapeData(R.drawable.ic_diamond, Color.valueOf(Color.GREEN), "Diamond"));

        importantEvents = new ImportantEvents() {
            @Override
            public void onButtonBind(MaterialButton button, int position) {
                button.setText(shapeList.get(position).getShapeText());
                button.setBackground(ContextCompat.getDrawable(requireContext(), shapeList.get(position).getShape()));
                button.setTextColor(Settings.ColorCalculator.getTextColor(Color.valueOf(requireContext().getColor(R.color.colorBackground))));
                button.setMinHeight(200);
                button.setMinWidth(200);
//                button.setBackgroundColor(shapeList.get(position).getColorValue()); //breaks everything
                button.setRippleColor(new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_pressed},
                        },
                        new int[]{
                                shapeList.get(position).getRippleColor(),
                        }
                ));

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (shapeList.get(position).equals(correctColor)) {
                            Toast.makeText(requireContext(), "Correct", Toast.LENGTH_SHORT).show();
                            currentPoints++;
                            pointsUpdated.onPointsChanged(currentPoints);
                            updateGame();
                        }
                    }
                });
            }
        };
        GameAdapter<ShapeData> shapeGameAdapter = new GameAdapter<>();
        shapeGameAdapter.setDataSet(shapeList);
        return shapeGameAdapter;
    }

    @Override
    GameView init() {
        setUI(true, true);
        currentLevel = 1;
        currentPoints = 0;
        return this;
    }

    static class ShapeData {
        Color color;
        @DrawableRes
        int shape;
        String shapeText;

        public ShapeData(@DrawableRes int shape, Color color, String shapeText) {
            this.color = color;
            this.shapeText = shapeText;
            this.shape = shape;
        }

        public @DrawableRes
        int getShape() {
            return shape;
        }

        Color getForegroundColor() {
            return Color.valueOf(getForegroundColorValue());
        }

        int getForegroundColorValue() {
            return Settings.ColorCalculator.getTextColor(color);
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

        public String getShapeText() {
            return shapeText;
        }
    }
}
