package com.www.funone.activities;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.www.funone.R;
import com.www.funone.adapters.GridHashTagContentAdapter;
import com.www.funone.model.HashTag;
import com.www.funone.util.Validator;

public class HashTagContentActivity extends BaseActivity {

    public static final String ARG_HASH_TAG = "arg_hash_tag";
    private HashTag mHashTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_tag_content);
        setUpToolBar();
        getBundleData();
        showHasTagName();
        setUpRecyclerView();
    }

    private void getBundleData() {
        if (Validator.isObjectValid(getIntent().getExtras())) {
            mHashTag = getIntent().getParcelableExtra(ARG_HASH_TAG);
        }
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showHasTagName() {
        if (Validator.isObjectValid(mHashTag)) {
            getSupportActionBar().setTitle(mHashTag.getName());
        }
    }

    private void setUpRecyclerView() {
        RecyclerView recViewGrid = (RecyclerView) findViewById(R.id.rec_view_grid);
        GridHashTagContentAdapter adapter = new GridHashTagContentAdapter();
        recViewGrid.setLayoutManager(new GridLayoutManager(this, 3));
        recViewGrid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
