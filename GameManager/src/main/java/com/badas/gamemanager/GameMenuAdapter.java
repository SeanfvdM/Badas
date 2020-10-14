package com.badas.gamemanager;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class GameMenuAdapter extends RecyclerView.Adapter<GameMenuAdapter.GameMenuViewHolder> {
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
            else if (menuItem.imageDrawable != null) {
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setImageDrawable(menuItem.imageDrawable);
            } else
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
