package com.badas.gamemanager;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Project: GameManager
 * By: Avremi
 * Converted By: Seanf
 * Created: 14,October,2020
 */
public class GameManagerFragment extends Fragment {
    private final MenuItemListener menuItemListener = new MenuItemListener() {
        @Override
        public void OnClickListener(GameMenuItem menuItem) {
//                switch (menuItem.tag) {
//                    case "math":
//                        startActivity(new Intent(temp.requireContext(), mathGames.class));
//                        break;
//                    case "puzzles":
//                        startActivity(new Intent(temp.requireContext(), puzzleGames.class));
//                        break;
//                    case "counting":
//                        startActivity(new Intent(temp.requireContext(), countingGames.class));
//                        break;
//                    case "word_cards":
//                        startActivity(new Intent(temp.requireContext(), wordCardGames.class));
//                        break;
//                    case "shape_colors":
//                        startActivity(new Intent(temp.requireContext(), shapeColoursGames.class));
//                        break;
//                }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_menu, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_Menu);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //special is every blank item, requireContext() will make that item span 2 columns instead of 1
                //set to 0 for every item to span 1 column
                //set to 1 for every item to span 2 columns
                //set to 2 for every even item to span 2 columns
                //set to 3 for every third item to span 2 columns
                //etc...

                int special = 5;
                return (position + 1) % special == 0 ? 2 : 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        List<GameMenuItem> gameMenuItems = new ArrayList<>();
        gameMenuItems.add(new GameMenuItem(
                "math",
                "Math",
                "Edit Math Games",
                Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.ic_math)),
                Color.valueOf(requireContext().getColor(R.color.purple)),
                menuItemListener
        ).setImageForegroundColor(Color.valueOf(Color.WHITE)));
        gameMenuItems.add(new GameMenuItem(
                "puzzles",
                "Puzzles",
                "Edit Puzzle Games",
                Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.ic_puzzlepiece2)),
                Color.valueOf(1f, 0.85f, 0.2f),
                menuItemListener
        ).setImageForegroundColor(Color.valueOf(Color.WHITE)));
        gameMenuItems.add(new GameMenuItem(
                "counting",
                "Counting",
                "Edit Counting Games",
                Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.ic_count)),
                Color.valueOf(requireContext().getColor(R.color.green)),
                menuItemListener
        ));
        gameMenuItems.add(new GameMenuItem(
                "word_cards",
                "Word Cards",
                "Edit Word Card Games",
                Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.ic_spelling)),
                Color.valueOf(requireContext().getColor(R.color.pink)),
                menuItemListener
        ));
        gameMenuItems.add(new GameMenuItem(
                "shape_colors",
                "Shapes and Colors",
                "Edit Shape and Color Games",
                Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.ic_shapes_and_colors)),
                Color.valueOf(requireContext().getColor(R.color.blue)),
                menuItemListener
        ));
        GameMenuAdapter gameMenuAdapter = new GameMenuAdapter(gameMenuItems);
        recyclerView.setAdapter(gameMenuAdapter);

        return view;
    }

    private Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(requireContext().getResources().getDisplayMetrics(), vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        int offset = 16;
        vectorDrawable.setBounds(offset, offset, canvas.getWidth() - offset, canvas.getHeight() - offset);
        vectorDrawable.draw(canvas);
        return bitmap;
    }
}


