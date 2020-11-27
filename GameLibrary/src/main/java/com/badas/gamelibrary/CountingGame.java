package com.badas.gamelibrary;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
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
public class CountingGame extends GameView {
    GridLayout layout;
    List<CountingData> countingList = new ArrayList<>();
    private CountingData correctCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.counting_layout, container, false);
        layout = view.findViewById(R.id.gl_layout);
        layout.setColumnCount(2);
        countingList.add(new CountingData(R.drawable.cartoon_fish, requireContext(), 1));
        countingList.add(new CountingData(R.drawable.cartoon_whale, requireContext(), 2));
        countingList.add(new CountingData(R.drawable.cartoon_fish, requireContext(), 3));
        countingList.add(new CountingData(R.drawable.cartoon_whale, requireContext(), 4));
        countingList.add(new CountingData(R.drawable.cartoon_fish, requireContext(), 5));
        countingList.add(new CountingData(R.drawable.cartoon_whale, requireContext(), 6));
        return view;
    }

    private void setCorrectCount() {
        //the range of the random
        float min = 0, max = 100;
        //the percentile of each index i.e.
        //for 4 values with a range of 0-100:
        //0-24 = 0, 25-49 = 1, 50-74 = 2, 75-100 = 3
        float percentile = (max - min) / countingList.size();
        float random = min + new Random().nextFloat() * (max - min);
        int counter = 1;

        while (counter <= countingList.size()) {
            if (percentile * counter > random) {
                if (correctCount != countingList.get(counter - 1)) {
                    correctCount = countingList.get(counter - 1);
                    break;
                } else
                    setCorrectCount();
            }
            counter++;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //todo change to 0 if bug fixed
        currentLevel = -1;
        currentPoints = -1;
        updateGame();
    }

    private void updateGame() {
        setCorrectCount();
        update(correctCount.item.value);
        update(correctCount.item.value);
        currentLevel++;
        pointsUpdated.onLevelChanged(currentLevel);
    }

    void update(int total) {
        if (layout.getChildCount() > 0)
            layout.removeAllViews();
        layout.setColumnCount(2);
        layout.setRowCount((int) Math.ceil((double) total / layout.getColumnCount()));
        int itemCount = 0;
        for (int r = 0; r < layout.getRowCount(); r++) {
            for (int c = 0; c < layout.getColumnCount(); c++) {
                if (itemCount == total) {
                    continue;
                }
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.setGravity(Gravity.CENTER);
                param.columnSpec = GridLayout.spec(c, 1);
                param.rowSpec = GridLayout.spec(r, 1);
                param.height = (((ImageView) countingList.get(itemCount).item.view).getDrawable().getBounds().bottom / layout.getRowCount()) / 6;
                param.width = (((ImageView) countingList.get(itemCount).item.view).getDrawable().getBounds().right / layout.getColumnCount()) / 3;
                countingList.get(itemCount).item.view.setLayoutParams(param);
                layout.addView(countingList.get(itemCount).item.view);
                itemCount++;
            }
        }
        if (layout.getChildCount() == 0) {
            update(total);
        }
        if (layout.getChildCount() != total) {
            correctCount = countingList.get(layout.getChildCount());
        }
    }

    @Override
    GameAdapter<?> getGameAdapter() {
        countingList.clear();

        importantEvents = new ImportantEvents() {
            @Override
            public void onButtonBind(MaterialButton button, int position) {
                button.setText(String.valueOf(countingList.get(position).item.value));
                button.setTextColor(Settings.ColorCalculator.getTextColor(Color.valueOf(button.getBackgroundTintList().getDefaultColor())));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (countingList.get(position).equals(correctCount)) {
                            Toast.makeText(requireContext(), "Correct", Toast.LENGTH_SHORT).show();
                            currentPoints++;
                            pointsUpdated.onPointsChanged(currentPoints);
                            updateGame();
                        }
                    }
                });
            }
        };
        GameAdapter<CountingData> countingGameAdapter = new GameAdapter<>();
        countingGameAdapter.setDataSet(countingList);
        return countingGameAdapter;
    }

    @Override
    GameView init() {
        setUI(true, true);
        currentLevel = 1;
        currentPoints = 0;
        return this;
    }

    static class CountingData {
        final String text;
        final @DrawableRes
        int image;
        final CountingItem<? extends View> item;

        public CountingData(@DrawableRes int image, Context context, int value) {
            this.text = "";
            this.image = image;
            this.item = new CountingItem<ImageView>(new ImageView(context), value);
            ((ImageView) this.item.getView()).setImageDrawable(ContextCompat.getDrawable(context, image));
            ((ImageView) this.item.getView()).setScaleType(ImageView.ScaleType.FIT_CENTER);
        }

        public CountingData(String text, Context context, int value) {
            this.text = text;
            this.image = -1;
            this.item = new CountingItem<TextView>(new TextView(context), value);
            ((TextView) this.item.getView()).setText(text);
        }
    }

    static class CountingItem<V extends View> {
        final int value;
        private final int id = hashCode();
        private final V view;

        public CountingItem(@NonNull V view, int value) {
            view.setId(id);
            this.view = view;
            this.value = value;
        }

        public V getView() {
            return view;
        }

        public int getId() {
            return id;
        }
    }
}
