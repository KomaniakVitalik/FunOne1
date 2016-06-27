package com.www.funone.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.www.funone.R;
import com.www.funone.adapters.LikedPostsAdapter;
import com.www.funone.util.SpacesItemDecoration;

public class LikedPostsFragment extends Fragment {

    private View mView;

    public LikedPostsFragment() {
        // Required empty public constructor
    }

    public static LikedPostsFragment newInstance(String param1, String param2) {
        LikedPostsFragment fragment = new LikedPostsFragment();
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
        mView = inflater.inflate(R.layout.fragment_liked_posts, container, false);
        setupView();
        return mView;
    }

    private void initView() {

    }

    private void setupView() {
        initView();

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView recViewGrid = (RecyclerView) mView.findViewById(R.id.rec_view_liked_posts);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_tiny);
        recViewGrid.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        LikedPostsAdapter adapter = new LikedPostsAdapter();
        recViewGrid.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recViewGrid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
