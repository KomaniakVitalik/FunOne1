package com.www.funone.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.www.funone.R;
import com.www.funone.adapters.ContentRecyclerAdapter;
import com.www.funone.model.Post;

import java.util.ArrayList;
import java.util.List;


public class ContentFragment extends Fragment {

    private List<Post> mPostsList = new ArrayList<>();
    private ContentRecyclerAdapter mAdapter;

    public ContentFragment() {
        // Required empty public constructor
    }

    public static ContentFragment newInstance() {
        ContentFragment fragment = new ContentFragment();
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
        View view = inflater.inflate(R.layout.content_fragmnet, container, false);
        getView(view);
        return view;

    }

    private void getView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rec_view_content);
        mAdapter = new ContentRecyclerAdapter(mPostsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Post post = new Post();
            post.setLiked(false);
            posts.add(post);
        }

        updateAdapter(posts);
    }

    private void updateAdapter(List<Post> posts) {
        mPostsList.clear();
        mPostsList.addAll(posts);
        mAdapter.notifyDataSetChanged();
    }

}
