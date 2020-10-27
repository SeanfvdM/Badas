package com.badas.gamelibrary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Project: BadasSolution
 * By: Seanf
 * Created: 27,October,2020
 */
public class GameFragmentTemplate extends Fragment {
    protected static GameFragmentTemplate instance;
    private static Fragment mFragmentToLoad = null;
    private static GameCallback gameCallback;

    public static GameFragmentTemplate getInstance() {
        if (instance == null)
            instance = new GameFragmentTemplate();
        return instance;
    }

    public static void setFragmentToLoad(Fragment fragmentToLoad) {
        mFragmentToLoad = fragmentToLoad;
    }

    public static GameCallback getGameCallback() {
        return gameCallback;
    }

    public static void setGameCallback(GameCallback gameCallback) {
        GameFragmentTemplate.gameCallback = gameCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_template, container, false);
    }

    public void loadChild() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.GameView, mFragmentToLoad).commit();
    }

    public void loadChild(AppCompatActivity activity) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.GameView, mFragmentToLoad).commit();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (gameCallback != null)
            gameCallback.parentAttached(this);
    }

    public abstract static class GameCallback {
        public void parentAttached(GameFragmentTemplate gameFragmentTemplate) {
            instance.loadChild();
        }

        public void parentCalled(AppCompatActivity compatActivity) {
            instance.loadChild(compatActivity);
        }
    }
}
