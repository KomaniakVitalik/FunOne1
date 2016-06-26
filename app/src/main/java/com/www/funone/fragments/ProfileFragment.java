package com.www.funone.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.www.funone.R;
import com.www.funone.adapters.ProfilePageAdapter;
import com.www.funone.view.TextViewFont;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private TextViewFont mEditPhoto;
    private TabLayout tabLayout;
    private ViewPager viewPager;


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

    private void setupViewPager() {
        viewPager = (ViewPager) mView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) mView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setSelectedTabIndicatorHeight(0);

        tabLayout.getTabAt(0).setCustomView(linkedPostsView());
        tabLayout.getTabAt(1).setCustomView(myPostsView());
    }

    private View linkedPostsView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.tab_linked_posts, null);
    }

    private View myPostsView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.tab_my_posts, null);
    }

    private void setupViewPager(ViewPager viewPager) {
        ProfilePageAdapter adapter = new ProfilePageAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new LinkedPostsFragment(), "ONE");
        adapter.addFragment(new MyPostsFragment(), "TWO");
        viewPager.setAdapter(adapter);
    }

    private void setupView() {
        initView();
        setupViewPager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_funone:
                break;
        }
    }
}
