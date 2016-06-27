package com.www.funone.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.www.funone.R;
import com.www.funone.adapters.AllCommentsAdapter;


public class CommentsFragment extends Fragment {

    private ListView mAllCommentsListView;


    public CommentsFragment() {
        // Required empty public constructor
    }

    public static CommentsFragment newInstance() {
        CommentsFragment fragment = new CommentsFragment();
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
        View view = inflater.inflate(R.layout.fragment_comments, container, false);
        setupView(view);
        return view;
    }

    /**
     * initialize all views in fragment
     */
    private void getView(View view) {
        mAllCommentsListView = (ListView) view.findViewById(R.id.lv_all_comments);
    }

    private void setupView(View view) {
        getView(view);
        setupList();
    }

    /**
     * initialize adapter and set it into list
     */
    private void setupList() {
        AllCommentsAdapter commentsAdapter = new AllCommentsAdapter(getContext());
        mAllCommentsListView.setAdapter(commentsAdapter);
    }


}
