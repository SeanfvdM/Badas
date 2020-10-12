package com.badas.profilemanager;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * Project: ProfileManager
 * By: Avremi
 * Converted By: Seanf
 * Created: 08,September,2020
 */

//converted the Activity to a fragment and moved it to its own library
public class ManagerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_manager, container, false);

        recyclerView = view.findViewById(R.id.my_recycler_viewer);
        //removed has fixed size
        // - this is good for memory and bad for if you know how many items to expect so rather just leave it as its default

        //use LinearLayoutManager or GridLayoutManager if you don't create your own
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        profileAdapter = new ProfileAdapter(userList, getChildFragmentManager());
        recyclerView.setAdapter(profileAdapter);

        profileAdapter.setListener(new ProfileAdapter.Listener() {
            @Override
            public void onBottomSheetOpen(ProfileEditBottomSheetFragment bottomSheet) {
                bottomSheet.setListener(new ProfileEditBottomSheetFragment.Listener() {
                    @Override
                    public void onConfirmListener(Profile newData, Profile oldData) {
                        for (int i = 0; i < userList.size(); i++) {
                            if (userList.get(i).equals(oldData)) {
                                userList.set(i, newData);
                                profileAdapter.notifyItemChanged(i);
                                break;
                            }
                        }
                    }
                });
            }

            @Override
            public void onRemoveUser(Profile deletedProfile) {
                //todo maybe look into adding a confirm dialog i.e. action dialog
                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).equals(deletedProfile)) {
                        userList.remove(i);
                        profileAdapter.notifyItemRemoved(i);
                        undoOption(deletedProfile,i);
                        break;
                    }
                }
            }
        });

        //todo add onclick for add profile

        return view;
    }

    public ArrayList<Profile> getUserList() {
        return userList;
    }

    public void attachDataSet(ArrayList<Profile> userList) {
        ManagerFragment.userList = userList;
        try{
            profileAdapter.notifyDataSetChanged();
        } catch (NullPointerException ignored) {
        }
        try{
            profileAdapter.setProfiles(userList);
        } catch (NullPointerException ignored) {
        }
    }

    public static void initDataSet(ArrayList<Profile> userList) {
        ManagerFragment.userList = userList;
    }

    private RecyclerView recyclerView;
    //removed the redundant layout manager variable
    private ProfileAdapter profileAdapter;
    public static ArrayList<Profile> userList = new ArrayList<>();

    //added undo option
    private void undoOption(final Profile profile, final int index){
        Snackbar snackbar = Snackbar.make(recyclerView, "Undo remove?", BaseTransientBottomBar.LENGTH_LONG);
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList.add(index,profile);
                profileAdapter.notifyItemInserted(index);
            }
        });
        snackbar.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        } else if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        }
    }
}