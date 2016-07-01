package com.www.funone.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.www.funone.R;
import com.www.funone.activities.MainActivity;
import com.www.funone.adapters.ProfilePageAdapter;
import com.www.funone.model.User;
import com.www.funone.util.Validator;
import com.www.funone.view.NonSwipableViewPager;
import com.www.funone.view.TextViewFont;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private TextViewFont mEditPhoto;
    private TextViewFont mTvUserName;
    private TabLayout tabLayout;
    private NonSwipableViewPager viewPager;
    private ImageView mIvProfileAva;
    private User mCurrentUser;


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
        mCurrentUser = ((MainActivity) getActivity()).getCurrentUser();
        setupView();

        return mView;
    }

    private void initView() {
        mEditPhoto = (TextViewFont) mView.findViewById(R.id.tv_edit_photo);
        mEditPhoto.onSetAlpha(37);

        RelativeLayout addFunOne = (RelativeLayout) mView.findViewById(R.id.btn_add_funone);
        addFunOne.setOnClickListener(this);

        CameraManager.getInstance().launch(getActivity(), CameraManager.Action.TAKE_PHOTO, mView.findViewById(R.id.img_add_user_photo));

    }

    private void setupViewPager() {
        viewPager = (NonSwipableViewPager) mView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setPagingEnabled(false);

        tabLayout = (TabLayout) mView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);

        tabLayout.setSelectedTabIndicatorHeight(0);

        tabLayout.getTabAt(0).setCustomView(linkedPostsView());
        tabLayout.getTabAt(1).setCustomView(myPostsView());

        tabLayout.getTabAt(0).getCustomView().setSelected(true);
    }


    private View linkedPostsView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.tab_liked_posts, null);
    }

    private View myPostsView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.tab_my_posts, null);
    }

    private void setupViewPager(ViewPager viewPager) {
        ProfilePageAdapter adapter = new ProfilePageAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new LikedPostsFragment(), null);
        adapter.addFragment(new MyPostsFragment(), null);
        viewPager.setAdapter(adapter);
    }

    private void setupView() {
        initView();
        setupViewPager();
        displayUserInfo();
    }

    private void displayUserInfo() {
        if (Validator.isObjectValid(mCurrentUser)) {
            showUserAva();
            showUserName();
        }
    }

    private void showUserAva() {
        mIvProfileAva = (ImageView) mView.findViewById(R.id.img_profile_ava);
        String picURL = mCurrentUser.getProfilePictureURL();
        if (Validator.isStringValid(picURL)) {
            Glide.with(getContext())
                    .load(picURL)
                    .centerCrop()
                    .into(mIvProfileAva);
        }
    }

    private void showUserName() {
        mTvUserName = (TextViewFont) mView.findViewById(R.id.tv_profile_user_name);
        String usName = mCurrentUser.getName();
        if (Validator.isStringValid(usName)) {
            mTvUserName.setText(usName);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_funone:
                break;
        }
    }

    @Override
    public void onPhotoTaken(String uri) {

    }
}
