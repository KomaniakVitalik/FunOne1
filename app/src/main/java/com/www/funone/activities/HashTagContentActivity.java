package com.www.funone.activities;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.www.funone.R;
import com.www.funone.adapters.GridHashTagContentAdapter;
import com.www.funone.adapters.SectionedGridRecyclerViewAdapter;
import com.www.funone.model.HashTag;
import com.www.funone.model.StubImage;
import com.www.funone.util.Validator;

import java.util.ArrayList;
import java.util.List;

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
        setPrimaryStatusBarColor(R.color.colorPrimaryDark);
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recViewGrid.setLayoutManager(gridLayoutManager);
        GridHashTagContentAdapter adapter = new GridHashTagContentAdapter();


        List<SectionedGridRecyclerViewAdapter.Section> sections =
                new ArrayList<>();

        //Sections
        sections.add(new SectionedGridRecyclerViewAdapter.Section(0, "Popular", "76,0810 posts"));
        sections.add(new SectionedGridRecyclerViewAdapter.Section(9, "Newest", "76,0810 posts"));
        sections.add(new SectionedGridRecyclerViewAdapter.Section(18, "Eldest", "76,0810 posts"));

        SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
        SectionedGridRecyclerViewAdapter mSectionedAdapter = new
                SectionedGridRecyclerViewAdapter(this, R.layout.item_header, R.id.tv_content_type, R.id.tv_posts_count, recViewGrid, adapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        recViewGrid.setAdapter(mSectionedAdapter);
        mSectionedAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
