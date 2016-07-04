package com.www.funone.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;

import com.www.funone.R;
import com.www.funone.util.AppSettings;
import com.www.funone.view.TextViewFont;

import java.util.Locale;

import io.realm.Realm;

public class SettingsActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    //never null as created in DataManager if null
    private AppSettings mAppSettings;
    private TextViewFont mTvGoogle;
    private TextViewFont mTvFacebook;
    private TextViewFont mTvVk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mAppSettings = getDataManager().getAppSettings();
        setUpToolBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        TextViewFont logOut = (TextViewFont) findViewById(R.id.tv_log_out);
        logOut.setOnClickListener(this);

        SwitchCompat autoPlay = (SwitchCompat) findViewById(R.id.switch_auto_play);
        autoPlay.setOnCheckedChangeListener(this);
        autoPlay.setChecked(mAppSettings.isAutoPlay());

        SwitchCompat notifications = (SwitchCompat) findViewById(R.id.switch_notifications);
        notifications.setOnCheckedChangeListener(this);
        notifications.setChecked(mAppSettings.isEnableNotifications());

        mTvVk = (TextViewFont) findViewById(R.id.tv_vk_status);
        mTvFacebook = (TextViewFont) findViewById(R.id.tv_facebook_status);
        mTvGoogle = (TextViewFont) findViewById(R.id.tv_google_status);

        showSocialNetworkStatus(mTvVk, mAppSettings.isVk());
        showSocialNetworkStatus(mTvFacebook, mAppSettings.isFacebook());
        showSocialNetworkStatus(mTvGoogle, mAppSettings.isGoogle());
    }

    private void setupView() {
        initView();
    }

    private void showSocialNetworkStatus(TextViewFont textViewFont, boolean connected) {
        if (connected) {
            textViewFont.setText(getResString(R.string.connected));
        } else {
            textViewFont.setText(getResString(R.string.disconnect));
        }
    }

    private void changeLanguage() {
        String languageToLoad = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_log_out:
                break;
        }
    }

    @Override
    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                switch (buttonView.getId()) {
                    case R.id.switch_auto_play:
                        mAppSettings.setAutoPlay(isChecked);
                        break;
                    case R.id.switch_notifications:
                        mAppSettings.setEnableNotifications(isChecked);
                        break;
                }
            }
        });
    }
}
