package com.badas.badasstyle;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public abstract class BadasFragment extends Fragment {
    protected static FloatingActionButton floatingActionButton;
    public static void setFloatingActionButton(FloatingActionButton floatingActionButton) {
        BadasFragment.floatingActionButton = floatingActionButton;
    }
}
