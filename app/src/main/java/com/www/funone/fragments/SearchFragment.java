package com.www.funone.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.www.funone.R;
import com.www.funone.activities.HashTagContentActivity;
import com.www.funone.activities.MainActivity;
import com.www.funone.adapters.GridHashTagContentAdapter;
import com.www.funone.adapters.HashTagRecyclerAdapter;
import com.www.funone.adapters.SectionedGridRecyclerViewAdapter;
import com.www.funone.model.HashTag;
import com.www.funone.util.Validator;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements HashTagRecyclerAdapter.OnHashTagClickListener {


    private HashTagRecyclerAdapter mHashTagAdapter;
    private GridHashTagContentAdapter mGridHashTagAdapter;
    private List<HashTag> mHashTags = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MainActivity mainActivity;

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
        mainActivity = (MainActivity) getActivity();

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rec_view_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initGridHashTagAdapter();
        initSearchFocusListener();
    }

    private void initSearchFocusListener() {
        ((MainActivity) getActivity()).addSearchFocusListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    initHashTagAdapter();
                }
            }
        });
    }

    private void initHashTagAdapter() {
        mainActivity.setSearchGridShown(false);
        mHashTagAdapter = new HashTagRecyclerAdapter(mHashTags);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mHashTagAdapter.setOnHashTagClickListener(this);
        mRecyclerView.setAdapter(mHashTagAdapter);
        stubData();
        mHashTagAdapter.notifyDataSetChanged();
    }

    private void initGridHashTagAdapter() {
        mainActivity.setSearchGridShown(true);
        mGridHashTagAdapter = new GridHashTagContentAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        List<SectionedGridRecyclerViewAdapter.Section> sections =
                new ArrayList<>();

        //Sections
        sections.add(new SectionedGridRecyclerViewAdapter.Section(0, "Popular", "76,0810 posts"));
        sections.add(new SectionedGridRecyclerViewAdapter.Section(9, "Newest", "76,0810 posts"));
        sections.add(new SectionedGridRecyclerViewAdapter.Section(18, "Eldest", "76,0810 posts"));

        SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];

        SectionedGridRecyclerViewAdapter mSectionedAdapter = new
                SectionedGridRecyclerViewAdapter(getContext(), R.layout.item_header, R.id.tv_content_type,
                R.id.tv_posts_count, mRecyclerView, mGridHashTagAdapter);

        mSectionedAdapter.setSections(sections.toArray(dummy));

        mRecyclerView.setAdapter(mSectionedAdapter);
    }

    private void updateAdapter(List<HashTag> tags) {
        mHashTags.clear();
        mHashTags.addAll(tags);
        mHashTagAdapter.notifyDataSetChanged();
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

    private void startHashTagContentActivity(HashTag tag) {
        Intent intent = new Intent(getActivity(), HashTagContentActivity.class);
        intent.putExtra(HashTagContentActivity.ARG_HASH_TAG, tag);
        getActivity().startActivity(intent);
    }

    @Override
    public void onDestroy() {

        if (Validator.isObjectValid(mHashTagAdapter)) {
            mHashTagAdapter.setOnHashTagClickListener(null);
        }
        super.onDestroy();
    }

    @Override
    public void onHashTagClicked(HashTag tag) {
        startHashTagContentActivity(tag);
    }

}
