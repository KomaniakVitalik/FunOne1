package com.www.funone.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

        getSupportActionBar().setTitle(getResString(R.string.all_comments));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * initialize all views in fragment
     */
    private void initView() {
        mAllCommentsListView = (ListView) findViewById(R.id.lv_all_comments);
    }

    private void setupView() {
        initView();
        setupList();
    }

    /**
     * initialize adapter and set it into list
     */
    private void setupList() {
        AllCommentsAdapter commentsAdapter = new AllCommentsAdapter(this);
        mAllCommentsListView.setAdapter(commentsAdapter);
    }
}