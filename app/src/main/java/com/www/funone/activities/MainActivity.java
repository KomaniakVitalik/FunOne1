package com.www.funone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.www.funone.R;
import com.www.funone.ViewPagerManager;
import com.www.funone.util.ViewUtil;

public class MainActivity extends BaseActivity {

    private AppBarLayout mBarLayout;
    private TextView mTvToolBarTitle;
    private boolean toolBarExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolBar();
        new ViewPagerManager(this);
    }

    /**********************************************************************************************/
    /**************************************** ToolBar *********************************************/
    /**********************************************************************************************/
    //ToolBar start region
    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mBarLayout.setExpanded(false);
        mTvToolBarTitle = (TextView) findViewById(R.id.tv_main_activity_title);
        setToolBarTitleText(getResString(R.string.search));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void expandToolBar() {
        mBarLayout.setExpanded(true, true);
        setToolBarBackAndTitleVisible(true);
        showToolBatTitle();
        setToolBarGravity(Gravity.TOP);
        hideSlidingTabs();
        toolBarExpanded = true;
    }

    public void collapseToolBar() {
        mBarLayout.setExpanded(false, true);
        setToolBarBackAndTitleVisible(false);
        hideToolBarTitle();
        setToolBarGravity(Gravity.BOTTOM);
        showSlidingTabs();
        toolBarExpanded = false;
    }

    private void setToolBarGravity(int gravity) {
        CollapsingToolbarLayout.LayoutParams params
                = (CollapsingToolbarLayout.LayoutParams) findViewById(R.id.toolbar).getLayoutParams();
        params.gravity = gravity;
        findViewById(R.id.toolbar).setLayoutParams(params);
    }

    private void hideSlidingTabs() {
        ViewUtil.hideView(findViewById(R.id.sliding_tabs));
    }

    private void showSlidingTabs() {
        ViewUtil.showView(findViewById(R.id.sliding_tabs));
    }

    private void setToolBarBackAndTitleVisible(boolean visible) {
        getSupportActionBar().setDisplayShowTitleEnabled(visible);
        getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
    }

    private void showToolBatTitle() {
        ViewUtil.showView(mTvToolBarTitle);
    }

    private void hideToolBarTitle() {
        ViewUtil.hideView(mTvToolBarTitle);
    }

    private void setToolBarTitleText(String text) {
        mTvToolBarTitle.setText(text);
    }
    //ToolBar end region

    /**********************************************************************************************/

    @Override
    public void onBackPressed() {
        if (toolBarExpanded) {
            collapseToolBar();
        } else {
            super.onBackPressed();
        }
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
            startActivity(new Intent(this, AllCommentActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
