package com.www.funone.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.www.funone.R;
import com.www.funone.view.TextViewFont;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private TextViewFont mEditPhoto;


    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_profile, container, false);

        setupView();

        return mView;
    }

    private void initView() {
        mEditPhoto = (TextViewFont) mView.findViewById(R.id.tv_edit_photo);
        mEditPhoto.onSetAlpha(37);

        RelativeLayout addFunOne = (RelativeLayout) mView.findViewById(R.id.btn_add_funone);
        addFunOne.setOnClickListener(this);
    }

    private void setupView() {
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_funone:
                break;
        }
    }
}
