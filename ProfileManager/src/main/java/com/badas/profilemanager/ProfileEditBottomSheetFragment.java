package com.badas.profilemanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Project: ProfileManager
 * By: Avremi
 * Reviewed By: Seanf
 * Created: 08,September,2020
 */
public class ProfileEditBottomSheetFragment extends BottomSheetDialogFragment {
    private Listener listener;
    private Profile profile;
    public Button btnConfirm;
    private TextInputEditText edtName, edtInfo;

    public ProfileEditBottomSheetFragment(Profile profile) {
        this.profile = profile;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_edit_bottom_sheet, container, false);
        edtName = view.findViewById(R.id.tiet_Name);
        edtInfo = view.findViewById(R.id.tiet_Info);
        btnConfirm = view.findViewById(R.id.btn_Confirm);
        edtName.setText(profile.getName());
        edtInfo.setText(profile.getUserInfo()); //todo maybe look into changing this

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo maybe look into adding a confirm dialog i.e. action dialog
                Profile oldData = profile;
                profile.setName(edtName.getText().toString());
                profile.setUserInfo(edtInfo.getText().toString());
                if (listener != null) {
                    listener.onConfirmListener(profile, oldData);
                }
                dismissAllowingStateLoss();
            }
        });
        return view;
    }

    //removed empty method

    public interface Listener {
        void onConfirmListener(Profile newData, Profile oldData);
    }
}
