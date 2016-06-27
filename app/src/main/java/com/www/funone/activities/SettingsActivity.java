package com.www.funone.activities;

import android.content.res.Configuration;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.www.funone.CoreApp;
import com.www.funone.R;
import com.www.funone.util.AppSettings;
import com.www.funone.util.Pref;
import com.www.funone.view.TextViewFont;

import java.util.Locale;

public class SettingsActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setUpToolBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Pref.getString(Pref.APP_SETTINGS_KEY) != null) {
            CoreApp.getInstance().setAppSettings(new Gson().fromJson(Pref.getString(Pref.APP_SETTINGS_KEY), AppSettings.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Pref.setString(Pref.APP_SETTINGS_KEY, new Gson().toJson(CoreApp.getInstance().getAppSettings()));
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
        autoPlay.setChecked(CoreApp.getInstance().getAppSettings().isAutoPlay());

        SwitchCompat notifications = (SwitchCompat) findViewById(R.id.switch_notifications);
        notifications.setOnCheckedChangeListener(this);
        notifications.setChecked(CoreApp.getInstance().getAppSettings().isEnableNotifications());
    }

    private void setupView() {
        initView();
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_auto_play:
                CoreApp.getInstance().getAppSettings().setAutoPlay(isChecked);
                break;
            case R.id.switch_notifications:
                CoreApp.getInstance().getAppSettings().setEnableNotifications(isChecked);
                break;
        }
    }
}
