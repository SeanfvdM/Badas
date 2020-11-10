package com.badas.badassolution.Menu;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badas.badassolution.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

public class SheetMenu extends BottomSheetDialogFragment {
    private final DialogMenuListener dialogMenuListener;
    private final NavController controller;
    private final SheetMenuItem[] menu;
    MenuAdapter menuAdapter = new MenuAdapter();

    SheetMenu(DialogMenuListener dialogMenuListener, NavController controller, SheetMenuItem[] menu) {
        this.dialogMenuListener = dialogMenuListener;
        this.controller = controller;
        this.menu = menu;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sheet_menu, container, false);
        RecyclerView menuHolder = view.findViewById(R.id.menuHolder);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            menuHolder.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        } else if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            menuHolder.setLayoutManager(new LinearLayoutManager(requireContext()));
        }
        menuHolder.setAdapter(menuAdapter);
        return view;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (dialogMenuListener != null)
            dialogMenuListener.onDismissed(false);
    }

    @Override
    public void dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
        if (dialogMenuListener != null)
            dialogMenuListener.onDismissed(true);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (dialogMenuListener != null)
            dialogMenuListener.onCanceled();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissAllowingStateLoss();
    }

    public interface DialogMenuListener {
        void onDismissed(boolean stateLost);

        void onCanceled();
    }

    public static class SheetMenuItem {
        private final NavDestination destination;
        private int icon;
        private String label;
        private int weight = 0;
        private MaterialButton button;

        public SheetMenuItem(NavDestination destination) {
            this.destination = destination;
            this.label = destination.getLabel().toString();
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public NavDestination getDestination() {
            return destination;
        }
    }

    private class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sheet_menu_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            try {
                holder.item.setIcon(holder.itemView.getContext().getDrawable(menu[position].getIcon()));
            } catch (Exception ignored) {
            }
            holder.item.setText(menu[position].getLabel());
            if (menu[position].getDestination() == controller.getCurrentDestination()) {
                holder.item.setClickable(false);
                holder.item.setChecked(true);
            }
            holder.item.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                    if (isChecked) {
                        for (SheetMenuItem item : menu) {
                            if (menu[position] == item && isChecked) {
                                item.button.setClickable(false);
                                item.button.setChecked(true);
                                controller.navigate(item.getDestination().getId());
                                dismiss();
                            } else {
                                item.button.setClickable(true);
                                item.button.setChecked(false);
                            }
                        }
                        menuAdapter.notifyDataSetChanged();
                    }
                }
            });
            menu[position].button = holder.item;
        }

        @Override
        public int getItemCount() {
            return menu.length;
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            MaterialButton item;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                item = itemView.findViewById(R.id.menuItem);
                item.setChecked(false);
            }
        }
    }

}

