package com.badas.gamelibrary;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badas.badasoptions.Settings;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: BadasSolution
 * By: Seanf
 * Created: 27,October,2020
 */
public class GameFragmentTemplate extends Fragment {
    public static String Game = "Template";

    protected static GameFragmentTemplate instance;
    protected static boolean usePoints = false, useLevels = false;
    private static GameView mFragmentToLoad = null;
    private static GameCallback gameCallback;
    private static int correctOption = -1;
    private static int[] options;
    TextView tv_points;
    TextView tv_levels;
    private RecyclerView recyclerView;

    public static GameFragmentTemplate init(GameView fragmentToLoad, GameCallback gameCallback) {
        instance = new GameFragmentTemplate();
        mFragmentToLoad = fragmentToLoad;
        GameFragmentTemplate.gameCallback = gameCallback;
        return instance;
    }

    public static GameCallback getGameCallback() {
        return gameCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_template, container, false);
        view.findViewById(R.id.fab_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getGameCallback() != null)
                    getGameCallback().doneClicked();
            }
        });
        tv_points = view.findViewById(R.id.tv_points);
        tv_levels = view.findViewById(R.id.tv_level);
        tv_points.setTypeface(Settings.Font.getTypeface());
        tv_levels.setTypeface(Settings.Font.getTypeface());
        ((Button) view.findViewById(R.id.fab_done)).setTypeface(Settings.Font.getTypeface());
        ((TextView) view.findViewById(R.id.textView3)).setTypeface(Settings.Font.getTypeface());
        ((TextView) view.findViewById(R.id.textView5)).setTypeface(Settings.Font.getTypeface());

        mFragmentToLoad.pointsUpdated = new GameView.PointsUpdated() {
            @Override
            public void onPointsChanged(int points) {
                tv_points.setText(points + "");
            }

            @Override
            public void onLevelChanged(int level) {
                tv_levels.setText(level + "");
            }
        };

        recyclerView = view.findViewById(R.id.rv_gameButtons);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerView.setAdapter(mFragmentToLoad.init().getGameAdapter());

        if (usePoints)
            view.findViewById(R.id.ll_points).setVisibility(View.VISIBLE);
        else
            view.findViewById(R.id.ll_points).setVisibility(View.GONE);

        if (useLevels)
            view.findViewById(R.id.ll_levels).setVisibility(View.VISIBLE);
        else
            view.findViewById(R.id.ll_levels).setVisibility(View.GONE);

        return view;
    }

    public void loadChild(@NonNull AppCompatActivity activity) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.GameView, mFragmentToLoad).commit();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public abstract static class GameCallback {

        public void parentCalled(AppCompatActivity compatActivity) {
            instance.loadChild(compatActivity);
        }

        @CallSuper
        public void doneClicked() {
            mFragmentToLoad = null;
            gameCallback = null;
            instance = null;
            options = new int[0];
            correctOption = -1;
            useLevels = false;
            usePoints = false;
            Log.i("GameView", "User quit the game");
        }
    }

    public abstract static class GameView extends Fragment {
        static ImportantEvents importantEvents;
        protected int currentPoints = 0, currentLevel = 0;
        PointsUpdated pointsUpdated;

        void setUI(boolean useLevels, boolean usePoints) {
            GameFragmentTemplate.useLevels = useLevels;
            GameFragmentTemplate.usePoints = usePoints;
        }

        abstract GameAdapter<?> getGameAdapter();

        public GameCallback getGameCallback() {
            return gameCallback;
        }

        abstract GameView init();

        protected interface ImportantEvents {
            void onButtonBind(MaterialButton button, int position);
        }

        protected abstract static class PointsUpdated {
            public abstract void onPointsChanged(int points);

            public abstract void onLevelChanged(int level);
        }

        public static class GameAdapter<T> extends RecyclerView.Adapter<GameAdapter.GameViewHolder<T>> {
            List<T> dataSet = new ArrayList<>();

            public void setDataSet(List<T> dataSet) {
                this.dataSet = dataSet;
            }

            @NonNull
            @Override
            public GameViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.game_button_template, parent, false);
                return new GameViewHolder<>(view);
            }

            @Override
            public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
                holder.button.setTypeface(Settings.Font.getTypeface());
                importantEvents.onButtonBind(holder.button, position);
            }

            @Override
            public int getItemCount() {
                return dataSet.size();
            }

            static class GameViewHolder<T> extends RecyclerView.ViewHolder {
                MaterialButton button;

                public GameViewHolder(@NonNull View itemView) {
                    super(itemView);
                    button = itemView.findViewById(R.id.gameButton);
                }
            }
        }
    }
}
