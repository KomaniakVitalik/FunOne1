package com.www.funone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.www.funone.R;
import com.www.funone.ViewPagerManager;
import com.www.funone.util.ViewUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    private AppBarLayout mBarLayout;
    private TextView mTvToolBarTitle;
    private boolean toolBarExpanded = false;
    private ViewPagerManager mViewPagerManager;
    private EditText mEdSearch;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPagerManager = new ViewPagerManager(this);
        setUpToolBar();
        addSearchTextWatcher();
    }

    /**********************************************************************************************/
    /**************************************** ToolBar *********************************************/
    /**********************************************************************************************/
    //ToolBar start region
    private void setUpToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        setNestedToolBarScrollEnabled(false);
        mBarLayout.setExpanded(false);
        mTvToolBarTitle = (TextView) findViewById(R.id.tv_main_activity_title);
        setToolBarTitleText(getResString(R.string.search));
        setPrimaryStatusBarColor(R.color.colorPrimaryDark);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void expandToolBar() {
        setNestedToolBarScrollEnabled(true);
        mBarLayout.setExpanded(true, true);
        setToolBarBackAndTitleVisible(true);
        showToolBatTitle();
        setToolBarGravity(Gravity.TOP);
        hideSlidingTabs();
        toolBarExpanded = true;
        showSearchView();
    }

    public void collapseToolBar() {
        setNestedToolBarScrollEnabled(false);
        hideSearchView();
        mBarLayout.setExpanded(false, true);
        setToolBarBackAndTitleVisible(false);
        hideToolBarTitle();
        setToolBarGravity(Gravity.BOTTOM);
        showSlidingTabs();
        toolBarExpanded = false;
    }

    private void setNestedToolBarScrollEnabled(boolean enabled) {
        CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams)
                findViewById(R.id.fake_tool_bar_view).getLayoutParams();
        if (!enabled) {
            TypedValue tv = new TypedValue();
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                params.height = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            }
        } else {
            params.height = getResources().getDimensionPixelSize(R.dimen.tool_bar_size);
        }
        findViewById(R.id.fake_tool_bar_view).setLayoutParams(params);
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
    /***************************************** Search *********************************************/
    /**********************************************************************************************/

    //Search start region
    private void showSearchView() {
        ViewUtil.showView(findViewById(R.id.rel_search));
    }

    private void hideSearchView() {
        ViewUtil.hideView(findViewById(R.id.rel_search));
    }

    private void addSearchTextWatcher() {
        findViewById(R.id.iv_clear_search).setOnClickListener(this);
        mEdSearch = (EditText) findViewById(R.id.ed_search);
        mEdSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**********************************************************************************************/

    @Override
    public void onBackPressed() {
        if (toolBarExpanded) {
            collapseToolBar();
            mViewPagerManager.returnToPreviousTab();
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

        if (id == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear_search:
                mEdSearch.setText("");
                break;
        }
    }
}
