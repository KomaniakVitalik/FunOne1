package com.www.funone.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.www.funone.R;
import com.www.funone.adapters.HashTagRecyclerAdapter;
import com.www.funone.model.HashTag;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements HashTagRecyclerAdapter.OnHashTagClickListener {

    private RecyclerView mRecViewSearch;
    private HashTagRecyclerAdapter mAdapter;
    private List<HashTag> mHashTags = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        getView(view);

        return view;
    }

    private void getView(View view) {
        mRecViewSearch = (RecyclerView) view.findViewById(R.id.rec_view_search);
        mRecViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HashTagRecyclerAdapter(mHashTags);
        mAdapter.setOnHashTagClickListener(this);
        mRecViewSearch.setAdapter(mAdapter);

        //TODO
        stubData();

    }

    private void updateAdapter(List<HashTag> tags) {
        mHashTags.clear();
        mHashTags.addAll(tags);
        mAdapter.notifyDataSetChanged();
    }

    private void stubData() {
        List<HashTag> tags = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            HashTag hashTag = new HashTag();
            hashTag.setName("#hashTagPost " + i);
            hashTag.setPostsNumber(i);
            tags.add(hashTag);
        }
        updateAdapter(tags);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.setOnHashTagClickListener(null);
    }

    @Override
    public void onHashTagClicked(HashTag tag) {
        Toast.makeText(getContext(), tag.getName(), Toast.LENGTH_SHORT).show();
    }
}
