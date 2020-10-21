package com.badas.gamelibrary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class LevelSelectionFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level_selection, container, false);
        return view;
    }
}

class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder> {

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_level, parent, false);
        return new LevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class LevelViewHolder extends RecyclerView.ViewHolder {

        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}

