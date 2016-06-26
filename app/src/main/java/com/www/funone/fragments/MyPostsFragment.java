package com.www.funone.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.www.funone.R;
import com.www.funone.adapters.MyPostsAdapter;

public class MyPostsFragment extends Fragment {

    private ListView mPostsListView;
    private View mView;


    public MyPostsFragment() {
        // Required empty public constructor
    }

    public static MyPostsFragment newInstance(String param1, String param2) {
        MyPostsFragment fragment = new MyPostsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_my_posts, container, false);

        setupView();

        return mView;
    }

    private void initView() {
        mPostsListView = (ListView) mView.findViewById(R.id.lv_my_posts);

    }

    private void setupView() {
        initView();

        setupPostsList();
    }

    private void setupPostsList() {
        MyPostsAdapter postsAdapter = new MyPostsAdapter(getActivity());
        mPostsListView.setAdapter(postsAdapter);

        mPostsListView.addHeaderView(header(), null, false);
        mPostsListView.addFooterView(header(), null, false);
    }

    private View header() {
        View viewHeader = getActivity().getLayoutInflater().inflate(R.layout.layout_header_and_footer, null);
        return viewHeader;
    }
}
