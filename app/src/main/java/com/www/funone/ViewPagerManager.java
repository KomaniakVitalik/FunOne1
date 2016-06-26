package com.www.funone;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.www.funone.activities.MainActivity;
import com.www.funone.fragments.ContentFragment;
import com.www.funone.fragments.ProfileFragment;
import com.www.funone.fragments.SearchFragment;
import com.www.funone.util.Validator;
import com.www.funone.view.NonSwipableViewPager;


/**
 * Responsible for ViewPager set up
 */
public class ViewPagerManager implements TabLayout.OnTabSelectedListener,
        ViewPager.OnPageChangeListener {

    private MainActivity mActivity;
    private NonSwipableViewPager mViewPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private int mPreviousSelectedPosition = -1;

    private int[] TAB_ICS_STABLE = {
            R.drawable.home,
            R.drawable.search,
            R.drawable.profile
    };

    private int[] TAB_ICS_SELECTED = {
            R.drawable.home_active,
            R.drawable.search,
            R.drawable.profile_active
    };


    public ViewPagerManager(MainActivity activity) {
        this.mActivity = activity;
        init();
    }


    private void init() {
        mViewPager = (NonSwipableViewPager) mActivity.findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(2);

        mViewPager.setPagingEnabled(true);

        mPagerAdapter = new ScreenSlidePagerAdapter(mActivity.getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        TabLayout tabLayout = (TabLayout) mActivity.findViewById(R.id.sliding_tabs);

        //adds ics to tabs
        if (Validator.isObjectValid(tabLayout)) {
            tabLayout.setupWithViewPager(mViewPager);
            tabLayout.setOnTabSelectedListener(this);
            TabLayout.Tab tab1;
            TabLayout.Tab tab2;
            TabLayout.Tab tab3;
            if (Validator.isObjectValid(tabLayout.getTabAt(0))) {
                tab1 = tabLayout.getTabAt(0);
                tab2 = tabLayout.getTabAt(1);
                tab3 = tabLayout.getTabAt(2);
                if (Validator.isObjectValid(tab1) && Validator.isObjectValid(tab2)) {
                    tab1.setIcon(TAB_ICS_SELECTED[0]);
                    tab2.setIcon(TAB_ICS_STABLE[1]);
                    tab3.setIcon(TAB_ICS_STABLE[2]);
                }
            }
        }
    }

    public void returnToPreviousTab(){
        if(mPreviousSelectedPosition != -1){
            mViewPager.setCurrentItem(mPreviousSelectedPosition);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (Validator.isObjectValid(tab)) {
            mViewPager.setCurrentItem(tab.getPosition());
            int pos = tab.getPosition();
            switch (pos) {
                case 0:
                    tab.setIcon(TAB_ICS_SELECTED[pos]);
                    mActivity.collapseToolBar();
                    mPreviousSelectedPosition = pos;
                    break;
                case 1:
                    //mActivity.startActivity(new Intent(mActivity, SearchActivity.class));
                    tab.setIcon(TAB_ICS_SELECTED[pos]);
                    mActivity.expandToolBar();
                    break;
                case 2:
                    tab.setIcon(TAB_ICS_SELECTED[pos]);
                    mActivity.collapseToolBar();
                    mPreviousSelectedPosition = pos;
                    break;
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (Validator.isObjectValid(tab)) {
            int pos = tab.getPosition();
            switch (pos) {
                case 0:
                    tab.setIcon(TAB_ICS_STABLE[pos]);
                    break;
                case 1:
                    tab.setIcon(TAB_ICS_STABLE[pos]);
                    break;
                case 2:
                    tab.setIcon(TAB_ICS_STABLE[pos]);
                    break;
            }
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
        }
    }

    public Fragment getVisibleFragment() {
        return mPagerAdapter.getItem(mViewPager.getCurrentItem());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * A simple pager adapter that represents 4 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        static final int NUM_PAGES = 3;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }


        @Override
        public Fragment getItem(int position) {
            Fragment frg = null;
            switch (position) {
                case 0:
                    frg = ContentFragment.newInstance();
                    break;
                case 1:
                    frg = SearchFragment.newInstance();
                    break;
                case 2:
                    frg = ProfileFragment.newInstance();
                    break;
            }
            return frg;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}
