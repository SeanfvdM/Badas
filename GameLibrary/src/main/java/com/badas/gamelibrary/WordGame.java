package com.badas.gamelibrary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
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
public class WordGame extends GameView {
    ImageView wordDisplay;
    List<WordData> wordList = new ArrayList<>();
    @DrawableRes
    int[] images = {
            R.drawable.apple,
            R.drawable.ball,
            R.drawable.car,
            R.drawable.cat,
            R.drawable.dog,
            R.drawable.grass,
            R.drawable.key,
            R.drawable.pen,
            R.drawable.slide,
            R.drawable.tree
    };
    String[] answers = {
            "Apple",
            "Ball",
            "Car",
            "Cat",
            "Dog",
            "Grass",
            "Key",
            "Pen",
            "Slide",
            "Tree"
    };
    private WordData correctWord;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_layout, container, false);
        wordDisplay = view.findViewById(R.id.wordDisplay);
        return view;
    }

    private void setCorrectColor() {
        //the range of the random
        float min = 0, max = 100;
        //the percentile of each index i.e.
        //for 4 values with a range of 0-100:
        //0-24 = 0, 25-49 = 1, 50-74 = 2, 75-100 = 3
        float percentile = (max - min) / wordList.size();
        float random = min + new Random().nextFloat() * (max - min);
        int counter = 1;

        while (counter <= wordList.size()) {
            if (percentile * counter > random) {
                if (correctWord != wordList.get(counter - 1)) {
                    correctWord = wordList.get(counter - 1);
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
        wordDisplay.setImageResource(correctWord.getWordImage());
        currentLevel++;
        pointsUpdated.onLevelChanged(currentLevel);
    }

    @Override
    GameAdapter<?> getGameAdapter() {
        wordList.clear();
        for (int i = 0; i < 3; i++) {
            int j = new Random().nextInt(answers.length);
            WordData wordData = new WordData(answers[j], images[j]);
            if (!wordList.contains(wordData))
                wordList.add(wordData);
            else
                i--;
        }

        importantEvents = new ImportantEvents() {
            @Override
            public void onButtonBind(MaterialButton button, int position) {
                button.setText(wordList.get(position).getWord());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (wordList.get(position).equals(correctWord)) {
                            Toast.makeText(requireContext(), "Correct", Toast.LENGTH_SHORT).show();
                            currentPoints++;
                            pointsUpdated.onPointsChanged(currentPoints);
                            updateGame();
                        }
                    }
                });
            }
        };
        GameAdapter<WordData> colorGameAdapter = new GameAdapter<>();
        colorGameAdapter.setDataSet(wordList);
        return colorGameAdapter;
    }

    @Override
    GameView init() {
        setUI(true, true);
        currentLevel = 1;
        currentPoints = 0;
        return this;
    }

    static class WordData {
        String word;
        @DrawableRes
        int wordImage;

        public WordData(String word, @DrawableRes int wordImage) {
            this.word = word;
            this.wordImage = wordImage;
        }

        public String getWord() {
            return word;
        }

        public int getWordImage() {
            return wordImage;
        }
    }
}
