package com.badas.badassolution.ChildScreen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.badas.badasoptions.Settings;
import com.badas.badassolution.ChildScreen.Games.GameSelectorFragmentTemplate;
import com.badas.badassolution.GameState;
import com.badas.badassolution.R;
import com.badas.gamelibrary.GameFragmentTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class MainChildActivity extends AppCompatActivity {
    private final HashMap<String, GameSelectorFragmentTemplate> gameSelectorFragments = new HashMap<>();
    private final boolean endlessButtonNav = true;
    private ViewPager2 viewPager;
    private GameSelectorPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_child);
        hideSystemUI();
        ((Button) findViewById(R.id.btn_forward)).setTypeface(Settings.Font.getTypeface());
        ((Button) findViewById(R.id.btn_backward)).setTypeface(Settings.Font.getTypeface());
        ((Button) findViewById(R.id.btn_play)).setTypeface(Settings.Font.getTypeface());

        gameSelectorFragments.put(GameState.Game_ColorGame,
                new GameSelectorFragmentTemplate()
                        .setTitle("Color Matching")
                        .setDescription("[Placeholder]Match the large color tile to the correct smaller tile.")
                        .setIcon(R.drawable.ic_shapes_and_colors)
                        .setColor(getResources().getColor(R.color.blue, getTheme())));
        gameSelectorFragments.put(GameState.Game_ShapeGame,
                new GameSelectorFragmentTemplate()
                        .setTitle("Shape Matching")
                        .setDescription("[Placeholder]Match the large shape tile to the correct smaller tile.")
                        .setIcon(R.drawable.ic_shapes_and_colors)
                        .setColor(getResources().getColor(R.color.blue, getTheme())));
        gameSelectorFragments.put(GameState.Game_CountGame,
                new GameSelectorFragmentTemplate()
                        .setTitle("Counting")
                        .setDescription("[Placeholder]Match the total words or image to the number\n(Note) First level does not load the images")
                        .setIcon(R.drawable.ic_count)
                        .setColor(getResources().getColor(R.color.green, getTheme())));

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new GameSelectorPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        findViewById(R.id.btn_forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == gameSelectorFragments.size() - 1 && endlessButtonNav)
                    viewPager.setCurrentItem(0, true);
                else
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });

        findViewById(R.id.btn_backward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == 0 && endlessButtonNav)
                    viewPager.setCurrentItem(gameSelectorFragments.size() - 1, true);
                else
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            }
        });

        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                GameFragmentTemplate gameFragmentTemplate = null;
                try {
                    gameFragmentTemplate = GameState.getGame(gameSelectorFragments.keySet().toArray()[viewPager.getCurrentItem()].toString(), new GameFragmentTemplate.GameCallback() {
                        @Override
                        public void parentCalled(AppCompatActivity compatActivity) {
                            super.parentCalled(compatActivity);//todo add stuff here
                        }

                        @Override
                        public void doneClicked() {
                            super.doneClicked();//todo add stuff here
                            findViewById(R.id.container).setVisibility(View.GONE);
                            getSupportFragmentManager().getFragments().clear();
                        }
                    });
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                    //these errors should never happen
                }
                transaction.replace(R.id.container, gameFragmentTemplate);
                transaction.addToBackStack(null);
                transaction.commit();
                GameFragmentTemplate.getGameCallback().parentCalled(MainChildActivity.this);
                findViewById(R.id.container).setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private class GameSelectorPagerAdapter extends FragmentStateAdapter {
        public String currentScreen;

        public GameSelectorPagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        public String getCurrentScreen() {
            return currentScreen;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            currentScreen = gameSelectorFragments.keySet().toArray()[position].toString();
            return gameSelectorFragments.get(currentScreen);
        }

        public Fragment getFragment(int position) {
            currentScreen = gameSelectorFragments.keySet().toArray()[position].toString();
            return gameSelectorFragments.get(currentScreen);
        }

        @Override
        public int getItemCount() {
            return gameSelectorFragments.size();
        }
    }
}
