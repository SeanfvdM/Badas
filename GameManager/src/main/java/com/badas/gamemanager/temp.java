package com.badas.gamemanager;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

interface MenuItemListener {
    void OnClickListener(GameMenuItem menuItem);
}

/**
 * Project: BadasSolution
 * By: Seanf
 * Created: 12,October,2020
 */
class temp extends AppCompatActivity {

    private MenuItemListener menuItemListener = new MenuItemListener() {
        @Override
        public void OnClickListener(GameMenuItem menuItem) {
//                switch (menuItem.tag) {
//                    case "math":
//                        startActivity(new Intent(temp.this, mathGames.class));
//                        break;
//                    case "puzzles":
//                        startActivity(new Intent(temp.this, puzzleGames.class));
//                        break;
//                    case "counting":
//                        startActivity(new Intent(temp.this, countingGames.class));
//                        break;
//                    case "word_cards":
//                        startActivity(new Intent(temp.this, wordCardGames.class));
//                        break;
//                    case "shape_colors":
//                        startActivity(new Intent(temp.this, shapeColoursGames.class));
//                        break;
//                }
        }
    };

    private static Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        int offset = 16;
        vectorDrawable.setBounds(offset, offset, canvas.getWidth() - offset, canvas.getHeight() - offset);
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_menu);

        RecyclerView recyclerView = findViewById(R.id.rv_Menu);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //special is every blank item, this will make that item span 2 columns instead of 1
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
                getBitmap((VectorDrawable) Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.ic_math))),
                Color.valueOf(getColor(R.color.purple)),
                menuItemListener
        ).setImageForegroundColor(Color.valueOf(Color.WHITE)));
        gameMenuItems.add(new GameMenuItem(
                "puzzles",
                "Puzzles",
                "Edit Puzzle Games",
                getBitmap((VectorDrawable) Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.ic_puzzlepiece2))),
                Color.valueOf(1f, 0.85f, 0.2f),
                menuItemListener
        ).setImageForegroundColor(Color.valueOf(Color.WHITE)));
        gameMenuItems.add(new GameMenuItem(
                "counting",
                "Counting",
                "Edit Counting Games",
                getBitmap((VectorDrawable) Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.ic_count))),
                Color.valueOf(getColor(R.color.green)),
                menuItemListener
        ));
        gameMenuItems.add(new GameMenuItem(
                "word_cards",
                "Word Cards",
                "Edit Word Card Games",
                getBitmap((VectorDrawable) Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.ic_spelling))),
                Color.valueOf(getColor(R.color.pink)),
                menuItemListener
        ));
        gameMenuItems.add(new GameMenuItem(
                "shape_colors",
                "Shapes and Colors",
                "Edit Shape and Color Games",
                getBitmap((VectorDrawable) Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.ic_shapes_and_colors))),
                Color.valueOf(getColor(R.color.blue)),
                menuItemListener
        ));
        GameMenuAdapter gameMenuAdapter = new GameMenuAdapter(gameMenuItems);
        recyclerView.setAdapter(gameMenuAdapter);

    }
}

class GameMenuItem {
    public String tag;
    public String title;
    public String extraText;
    public Bitmap image;
    public Color imageBackgroundColor;
    public Color imageForegroundColor;
    private MenuItemListener menuItemListener;

    public GameMenuItem(String tag, String title, String extraText, Bitmap image, Color imageBackgroundColor, MenuItemListener menuItemListener) {
        this.tag = tag;
        this.title = title;
        this.extraText = extraText;
        this.image = image;
        this.imageBackgroundColor = imageBackgroundColor;
        this.menuItemListener = menuItemListener;
    }

    public GameMenuItem(String tag, String title, String extraText, Bitmap image, Color imageBackgroundColor) {
        this.tag = tag;
        this.title = title;
        this.extraText = extraText;
        this.image = image;
        this.imageBackgroundColor = imageBackgroundColor;
    }

    public GameMenuItem setImageForegroundColor(Color imageForegroundColor) {
        this.imageForegroundColor = imageForegroundColor;
        return this;
    }

    MenuItemListener getMenuItemListener() {
        return menuItemListener;
    }

    public void setMenuItemListener(MenuItemListener menuItemListener) {
        this.menuItemListener = menuItemListener;
    }
}

class GameMenuAdapter extends RecyclerView.Adapter<GameMenuAdapter.GameMenuViewHolder> {
    private List<GameMenuItem> menuItems;

    public GameMenuAdapter(List<GameMenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Deprecated
    public void setMenuItems(List<GameMenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public GameMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_menu_item, parent, false);
        return new GameMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameMenuViewHolder holder, int position) {
        holder.setData(menuItems.get(position));
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    static class GameMenuViewHolder extends RecyclerView.ViewHolder {
        GameMenuItem menuItem;
        ShapeableImageView imageView;
        TextView titleView, extraView;
        MaterialCardView cardView;

        public GameMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_GameImage);
            titleView = itemView.findViewById(R.id.tv_Title);
            extraView = itemView.findViewById(R.id.tv_Extra);
            cardView = itemView.findViewById(R.id.cv_MenuItem);
        }

        public void setData(GameMenuItem item) {
            this.menuItem = item;
            if (menuItem.image != null)
                imageView.setImageBitmap(menuItem.image);
            else
                imageView.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_baseline_gamepad_24));

            if (menuItem.imageBackgroundColor != null)
                imageView.setBackgroundColor(menuItem.imageBackgroundColor.toArgb());
            else
                imageView.setBackgroundColor(itemView.getResources().getColor(R.color.colorAccent, itemView.getContext().getTheme()));

            if (menuItem.imageForegroundColor != null) {
                ImageViewCompat.setImageTintMode(imageView, PorterDuff.Mode.SRC_IN);
                ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(menuItem.imageForegroundColor.toArgb()));
            }


            if (!(menuItem.extraText == null || TextUtils.isEmpty(menuItem.extraText)))
                extraView.setVisibility(View.VISIBLE);
            extraView.setText(menuItem.extraText);
            titleView.setText(menuItem.title);

            if (menuItem.getMenuItemListener() != null)
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuItem.getMenuItemListener().OnClickListener(menuItem);
                    }
                });
        }
    }
}
