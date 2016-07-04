package com.www.funone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.www.funone.CoreApp;
import com.www.funone.R;
import com.www.funone.managers.AuthenticationManager;
import com.www.funone.model.User;
import com.www.funone.util.Logger;
import com.www.funone.util.Pref;

public class SocialLoginActivity extends BaseActivity implements View.OnClickListener, AuthenticationManager.OnSocialLogInListener {

    public static final String TAG = "SocialLoginActivity";
    public static final String FONT_LUCIDA = "lucida-grande-bold.ttf";
    private boolean mLock = false;

    private AuthenticationManager mAuthenticationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthenticationManager = CoreApp.getInstance().getAuthenticationManager();
        mAuthenticationManager.addLogInListener(this);
        setContentView(R.layout.activity_socila_login);
        setUpToolBar();
        setTitleTypeFace();
        setPrimaryStatusBarColor(R.color.black);
    }

    /**
     * Adds click listener for buttons
     */
    private void setButtonListeners(View.OnClickListener listener) {
        findViewById(R.id.google_plus_log_in_button).setOnClickListener(listener);
        findViewById(R.id.rel_facebook_btn_wrapper).setOnClickListener(listener);
        findViewById(R.id.rel_terms_of_services).setOnClickListener(listener);
        findViewById(R.id.vk_login_btn).setOnClickListener(listener);
    }

    /**
     * Initializes Toolbar. Enables title and back button
     */
    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResString(R.string.log_in));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Applies LucidaGrande-Bold font to title TextViews
     */
    private void setTitleTypeFace() {
        TextView tvFun = (TextView) findViewById(R.id.tv_fun_one);
        TextView tvJoin = (TextView) findViewById(R.id.tv_join_have_fun);
        applyCustomFont(tvFun, FONT_LUCIDA);
        applyCustomFont(tvJoin, FONT_LUCIDA);
        spanWord(tvFun, 4, tvFun.getText().length(), getCompatColor(R.color.yellow));
    }

    /**
     * Starts Main Activity after successful log in
     */
    private void startMainActivity(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        SocialLoginActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        startMainActivity(null);
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setButtonListeners(this);
        mLock = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuthenticationManager.addLogInListener(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AuthenticationManager.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        if (!mLock) {
            mLock = true;
            switch (v.getId()) {
                case R.id.rel_facebook_btn_wrapper:
                    mAuthenticationManager.logInVia(SocialLoginActivity.this, AuthenticationManager.FACEBOOK);
                    break;
                case R.id.google_plus_log_in_button:
                    mAuthenticationManager.logInVia(SocialLoginActivity.this, AuthenticationManager.GOOGLE);
                    break;
                case R.id.vk_login_btn:
                    mAuthenticationManager.logInVia(SocialLoginActivity.this, AuthenticationManager.VK);
                    break;
                case R.id.rel_terms_of_services:
                    Toast.makeText(SocialLoginActivity.this, getResString(R.string.terms_privacy_policy)
                            , Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


    @Override
    public void onLogInSuccess(int socialNetworkKey, User user) {
        getDataManager().saveOrUpdateUser(user);
        startMainActivity(user);
    }

    @Override
    public void onLogInError(int message) {
        TOAST(message);
        mLock = false;
    }
}
