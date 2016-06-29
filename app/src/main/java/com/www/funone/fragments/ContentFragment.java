package com.www.funone.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.www.funone.R;
import com.www.funone.activities.AllCommentActivity;
import com.www.funone.activities.BestCommentsActivity;
import com.www.funone.activities.SocialLoginActivity;
import com.www.funone.adapters.ContentRecyclerAdapter;
import com.www.funone.model.Post;
import com.www.funone.util.Pref;

import java.util.ArrayList;
import java.util.List;


public class ContentFragment extends Fragment implements ContentRecyclerAdapter.OnPostInteractionListener,
        SlidingUpPanelLayout.PanelSlideListener,
        View.OnClickListener {

    public static final String TAG = "ContentFragment";
    private List<Post> mPostsList = new ArrayList<>();
    private ContentRecyclerAdapter mAdapter;
    private SlidingUpPanelLayout mSlidingUpPanelLayout;


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
        mAdapter.setOnPostInteractionListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        mSlidingUpPanelLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);
        mSlidingUpPanelLayout.addPanelSlideListener(this);
        mSlidingUpPanelLayout.setTouchEnabled(false);

        mSlidingUpPanelLayout.findViewById(R.id.lin_share).setOnClickListener(this);
        mSlidingUpPanelLayout.findViewById(R.id.lin_save).setOnClickListener(this);
        mSlidingUpPanelLayout.findViewById(R.id.lin_cancel).setOnClickListener(this);

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

    private void startBestCommentsActivity(Post post) {
        Intent intent = new Intent(getActivity(), BestCommentsActivity.class);
        intent.putExtra(BestCommentsActivity.ARG_POST, post);
        startActivity(intent);
    }

    private void showOrHideShareLayout() {
        if (mSlidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            mSlidingUpPanelLayout.setTouchEnabled(true);
            mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        } else {
            mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            mSlidingUpPanelLayout.setTouchEnabled(false);
        }
    }

    private void startAuthorizationNeededAlert() {
        if (!isUserLoggedIn()) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Authorization required")
                    .setMessage("In order to make changes - you have to be an authorized user")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startSocialLogInActivity();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    private boolean isUserLoggedIn() {
        return Pref.getBoolean(Pref.PREF_USER_LOGGED_IN);
    }

    private void startSocialLogInActivity() {
        getActivity().finish();
        getActivity().startActivity(new Intent(getActivity(), SocialLoginActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.setOnPostInteractionListener(null);
    }

    @Override
    public void onPostLiked(Post post) {
        startAuthorizationNeededAlert();
    }

    @Override
    public void onOpenBestCommentsActivity(Post post) {
        if (isUserLoggedIn()) {
            startBestCommentsActivity(post);
        }
        startAuthorizationNeededAlert();
    }

    @Override
    public void onOpenShareLayout(Post post) {
        showOrHideShareLayout();
        startAuthorizationNeededAlert();
    }

    @Override
    public void onOpenALLComments(Post post) {
        getActivity().startActivity(new Intent(getActivity(), AllCommentActivity.class));
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {

    }

    @Override
    public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
        if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            mSlidingUpPanelLayout.setTouchEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_share:
                break;
            case R.id.lin_save:
                break;
            case R.id.lin_cancel:
                showOrHideShareLayout();
                break;
        }
    }
}
