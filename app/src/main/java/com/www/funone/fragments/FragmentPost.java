package com.www.funone.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.www.funone.R;

public class FragmentPost extends Fragment {

    public FragmentPost() {
        // Required empty public constructor
    }


    public static FragmentPost newInstance() {
        FragmentPost fragment = new FragmentPost();
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
        View view = inflater.inflate(R.layout.fragment_fragment_post, container, false);
        getView(view);
        return view;
    }

    private void getView(View view) {

    }

}
