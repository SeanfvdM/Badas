package com.badas.badassolution;

import com.badas.gamelibrary.ColorGame;
import com.badas.gamelibrary.CountingGame;
import com.badas.gamelibrary.GameFragmentTemplate;
import com.badas.gamelibrary.ShapeGame;
import com.badas.gamelibrary.WordGame;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class GameState {
    public static final String Game_ColorGame = "ColorGame";
    public static final String Game_ShapeGame = "ShapeGame";
    public static final String Game_CountGame = "CountingGame";
    public static final String Game_WordGame = "WordGame";
    private static GameState instance;
    private final HashMap<String, Class<? extends GameFragmentTemplate.GameView>> gamesList = new HashMap<>();

    public static void init() {
        if (instance == null) {
            instance = new GameState();
            instance.gamesList.put(Game_ColorGame, ColorGame.class);
            instance.gamesList.put(Game_ShapeGame, ShapeGame.class);
            instance.gamesList.put(Game_CountGame, CountingGame.class);
            instance.gamesList.put(Game_WordGame, WordGame.class);
        }
    }

    public static GameFragmentTemplate getGame(String game, GameFragmentTemplate.GameCallback gameCallback) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        for (int i = 0; i < instance.gamesList.size(); i++) {
            if (instance.gamesList.containsKey(game)) {
                return GameFragmentTemplate.init(
                        (GameFragmentTemplate.GameView) instance.gamesList.get(game).getConstructors()[0].newInstance(),
                        gameCallback);
            }

        }
        throw new NullPointerException("Given class does not exist in the games list");
    }

}
