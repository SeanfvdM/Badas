package com.badas.profilemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

/**
 * Project: ProfileManager
 * By: Avremi
 * Reviewed By: Seanf
 * Created: 08,September,2020
 */
public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private ArrayList<Profile> profiles;
    private FragmentManager fragmentManager;
    private Listener listener;
    //removed redundant bottom sheet getter and variable

    public ProfileAdapter(ArrayList<Profile> profiles, FragmentManager fragmentManager) {
        this.profiles = profiles;
        this.fragmentManager = fragmentManager;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inlined code
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_card, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        final Profile currentItem = profiles.get(position);
        holder.iv_profileIcon.setImageResource(currentItem.getImageResource());//todo update to take a bitmap
        holder.tv_name.setText(currentItem.getName());
        holder.tv_extraInfo.setText(currentItem.getUserInfo());
        //todo add user type setting
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileEditBottomSheetFragment bottomSheet = new ProfileEditBottomSheetFragment(currentItem);
                bottomSheet.show(fragmentManager, "bottom sheet");
                if (listener != null) {
                    listener.onBottomSheetOpen(bottomSheet);
                }
            }
        });

        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRemoveUser(currentItem);
                }
            }
        });

        holder.btn_addFlash.setEnabled(false); //todo remove when action is available
        holder.btn_addFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo add a flashcard for games
            }
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_profileIcon;
        //todo add text view for user type
        public TextView tv_name, tv_extraInfo;
        public MaterialButton btn_edit, btn_remove, btn_addFlash;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_profileIcon = itemView.findViewById(R.id.iv_UserImage);
            tv_name = itemView.findViewById(R.id.tv_UserName);
            tv_extraInfo = itemView.findViewById(R.id.tv_ExtraInfo);
            btn_edit = itemView.findViewById(R.id.btn_Edit);
            btn_remove = itemView.findViewById(R.id.btn_Remove);
            btn_addFlash = itemView.findViewById(R.id.btn_AddFlash);
        }
    }

    public interface Listener {
        void onBottomSheetOpen(ProfileEditBottomSheetFragment bottomSheet);
        void onRemoveUser(Profile deletedProfile);
    }
}
