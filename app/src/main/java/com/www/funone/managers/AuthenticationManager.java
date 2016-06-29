package com.www.funone.managers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;
import com.www.funone.CoreApp;
import com.www.funone.activities.SocialLoginActivity;
import com.www.funone.model.User;
import com.www.funone.util.Logger;
import com.www.funone.util.Validator;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by vitaliy.herasymchuk on 6/29/16.
 */
public class AuthenticationManager {

    public static final String TAG = "AuthenticationManager";

    public static final int FACEBOOK = 1;
    public static final int GOOGLE = 2;
    public static final int VK = 3;

    private OnSocialLogInListener mOnSocialLogInListener = null;
    private static AuthenticationManager instance;

    private CallbackManager mFacebookCallBackManager;

    public static AuthenticationManager getInstance() {
        if (!Validator.isObjectValid(instance)) {
            instance = new AuthenticationManager();
        }
        return instance;
    }

    private Context mContext = CoreApp.getInstance();


    private AuthenticationManager() {
        FacebookSdk.sdkInitialize(CoreApp.getInstance().getApplicationContext());
        mFacebookCallBackManager = CallbackManager.Factory.create();
        registerFacebookAuthCallBack();
    }

    /**
     * Use this method to authenticate via a social network
     *
     * @param activity      - Activity
     * @param socialNetwork - FACEBOOK,GOOGLE,VK
     */
    public void logInVia(SocialLoginActivity activity, int socialNetwork) {
        switch (socialNetwork) {
            case FACEBOOK:
                LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email", "public_profile"));
                break;
            case GOOGLE:
                break;
            case VK:
                break;
        }
    }

    /**
     * Use this method to log out user from app basing on provided social network
     *
     * @param socialNetwork - FACEBOOK,GOOGLE,VK
     */
    public void logOutFrom(int socialNetwork) {
        switch (socialNetwork) {
            case FACEBOOK:
                LoginManager.getInstance().logOut();
                break;
            case GOOGLE:
                break;
            case VK:
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFacebookCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**********************************************************************************************/
    /******************************************** Facebook ****************************************/
    /**********************************************************************************************/

    private void registerFacebookAuthCallBack() {
        LoginManager.getInstance().registerCallback(mFacebookCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                createFacebookGraphRequest(loginResult);
            }

            @Override
            public void onCancel() {
                Logger.d(TAG, "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Logger.e(TAG, "onError " + error.toString());
                notifyLogInError(FACEBOOK);
            }
        });
    }

    private void createFacebookGraphRequest(LoginResult loginResult) {
        GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (Validator.isObjectValid(response)) {
                    if (Validator.isObjectValid(response.getError())) {
                        Logger.e(TAG, response.getError().getErrorMessage());
                        notifyLogInError(FACEBOOK);
                    } else {
                        if (Validator.isObjectValid(object)) {
                            Logger.d(TAG, object.toString());

                            User user = new Gson().fromJson(object.toString(), User.class);
                            getFacebookUserProfilePicture(user);
                        }
                    }
                }
            }
        }).executeAsync();
    }

    public void getFacebookUserProfilePicture(final User user) {
        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            try {
                                JSONObject data = response.getJSONObject();
                                if (data.has("picture")) {
                                    String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                    user.setProfilePictureURL(profilePicUrl);
                                    notifyLogInSuccess(FACEBOOK, user);
                                } else {
                                    notifyLogInSuccess(FACEBOOK, user);
                                }
                            } catch (Exception e) {
                                notifyLogInSuccess(FACEBOOK, user);
                                e.printStackTrace();
                            }
                        } else {
                            notifyLogInSuccess(FACEBOOK, user);
                        }
                    }
                }).executeAsync();
    }

    //Facebook login End Region

    /**********************************************************************************************/

    private String getDebugKeyHash() {
        String hash = "";
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                hash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("KeyHash:", hash);
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {

        }
        return hash;
    }

    public void addLogInListener(OnSocialLogInListener listener) {
        this.mOnSocialLogInListener = listener;
    }

    private void notifyLogInSuccess(int socialNetworkKey, User user) {
        if (Validator.isObjectValid(mOnSocialLogInListener)) {
            if (Validator.isObjectValid(user)) {
                mOnSocialLogInListener.onLogInSuccess(socialNetworkKey, user);
            }
        }
    }

    private void notifyLogInError(int socialNetworkKey) {
        if (Validator.isObjectValid(mOnSocialLogInListener)) {
            mOnSocialLogInListener.onLogInError(socialNetworkKey);
        }
    }

    public interface OnSocialLogInListener {
        void onLogInSuccess(int socialNetworkKey, User user);

        void onLogInError(int socialNetworkKey);
    }


}
