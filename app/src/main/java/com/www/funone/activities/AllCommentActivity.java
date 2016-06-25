package com.www.funone.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.www.funone.R;
import com.www.funone.adapters.AllCommentsAdapter;

public class AllCommentActivity extends BaseActivity {

    private ListView mAllCommentsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comment);
        setUpToolBar();
        setupView();
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getResString(R.string.settings));
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setupView();
    }

    private void initView() {
        mAllCommentsListView = (ListView) findViewById(R.id.lv_all_comments);
    }

    private void setupView() {
        initView();
        setupList();
    }

    private void setupList() {
        AllCommentsAdapter commentsAdapter = new AllCommentsAdapter(this);
        mAllCommentsListView.setAdapter(commentsAdapter);
    }
}
