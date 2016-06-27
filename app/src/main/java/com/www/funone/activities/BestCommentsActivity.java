package com.www.funone.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.www.funone.R;
import com.www.funone.fragments.CommentsFragment;
import com.www.funone.view.NonSwipableViewPager;

import java.util.ArrayList;
import java.util.List;

public class BestCommentsActivity extends BaseActivity {

    public static final String ARG_POST = "post";

    private TabLayout tabLayout;
    private NonSwipableViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_comments);
        setUpToolBar();
        setupView();
        removeIncludedLayoutMarginBottom();
        resizeIncludedLayoutImageView();
    }

    private void resizeIncludedLayoutImageView() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) findViewById(R.id.iv_post_image).getLayoutParams();
        params.height = getResources().getDimensionPixelSize(R.dimen.post_image_comments_height);
        findViewById(R.id.iv_post_image).setLayoutParams(params);
    }

    private void removeIncludedLayoutMarginBottom() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) findViewById(R.id.rel_margin).getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        findViewById(R.id.rel_margin).setLayoutParams(params);
    }

    /**
     * Initializes Toolbar. Enables title and back button
     */
    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResString(R.string.best_comments));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setupViewPager() {
        viewPager = (NonSwipableViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setPagingEnabled(false);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);

        tabLayout.setSelectedTabIndicatorHeight(0);

        tabLayout.getTabAt(0).setCustomView(bestCommentsView());
        tabLayout.getTabAt(1).setCustomView(allCommentsView());

        tabLayout.getTabAt(0).getCustomView().setSelected(true);
    }


    private View bestCommentsView() {
        return LayoutInflater.from(this).inflate(R.layout.tab_best_comments, null);
    }

    private View allCommentsView() {
        return LayoutInflater.from(this).inflate(R.layout.tab_all_comments, null);
    }


    private void setupViewPager(ViewPager viewPager) {
        CommentsPageAdapter adapter = new CommentsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(CommentsFragment.newInstance(), null);
        adapter.addFragment(CommentsFragment.newInstance(), null);
        viewPager.setAdapter(adapter);
    }

    private void setupView() {
        setupViewPager();
    }

    public class CommentsPageAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public CommentsPageAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
