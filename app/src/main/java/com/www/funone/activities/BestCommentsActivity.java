package com.www.funone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.www.funone.R;
import com.www.funone.adapters.ProfilePageAdapter;
import com.www.funone.fragments.CommentsFragment;
import com.www.funone.fragments.FragmentPost;


public class BestCommentsActivity extends BaseActivity implements View.OnClickListener {

    public static final String ARG_POST = "post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_comments);
        setUpToolBar();
        loadFrg(CommentsFragment.newInstance(), R.id.content_best_comments, false);
        findViewById(R.id.all_comments).setOnClickListener(this);
        findViewById(R.id.best_comments).setOnClickListener(this);
        setPrimaryStatusBarColor(R.color.colorPrimaryDark);
        initViewPager();
    }

    private void initViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager_best_comments);
        viewPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));

    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentPost.newInstance();
        }

        @Override
        public int getCount() {
            return 10;
        }
    }


    /**
     * Initializes Toolbar. Enables title and back button
     */
    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.best_comments).setSelected(true);
        findViewById(R.id.all_comments).setSelected(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_comments:
                findViewById(R.id.best_comments).setSelected(false);
                findViewById(R.id.all_comments).setSelected(true);
                startActivity(new Intent(this, AllCommentActivity.class));
                break;
        }
    }
}
