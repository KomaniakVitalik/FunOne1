package com.www.funone.managers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.util.VKUtil;
import com.www.funone.CoreApp;
import com.www.funone.model.User;
import com.www.funone.util.Logger;
import com.www.funone.util.Validator;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vitaliy.herasymchuk on 6/29/16.
 */
public class AuthenticationManager {

    public static final String TAG = "AuthenticationManager";

    public static final int FACEBOOK = 1;
    public static final int GOOGLE = 2;
    public static final int VK = 3;

    public static final int RC_SIGN_IN_GOOGLE = 9009;

    private OnSocialLogInListener mOnSocialLogInListener = null;
    private static AuthenticationManager instance;

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
        VKSdk.initialize(CoreApp.getInstance().getApplicationContext());
        String[] fingerprints = VKUtil.getCertificateFingerprint(CoreApp.getInstance(), CoreApp.getInstance().getPackageName());
        for (String s : Arrays.asList(fingerprints)) {
            Logger.d(TAG, "finger VK " + s);
        }
    }

    /**
     * Use this method to authenticate via a social network
     *
     * @param activity      - Activity
     * @param socialNetwork - FACEBOOK,GOOGLE,VK
     */
    public void logInVia(AppCompatActivity activity, int socialNetwork) {
        switch (socialNetwork) {
            case FACEBOOK:
                LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email", "public_profile"));
                break;
            case GOOGLE:
                signInViaGoogle(activity);
                break;
            case VK:
                signInViaVK(activity);
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
                signOutFromGoogleAccount();
                break;
            case VK:
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFacebookCallBackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        } else {
            handleVKSignInResult(requestCode, resultCode, data);
        }

    }

    /**********************************************************************************************/
    /******************************************** Facebook ****************************************/
    /**********************************************************************************************/

    private CallbackManager mFacebookCallBackManager;

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
    /***************************************  GOOGLE  *********************************************/
    /**********************************************************************************************/

    private GoogleApiClient mGoogleApiClient;

    private void signInViaGoogle(AppCompatActivity activity) {
        initGoogleApiClientWithOptions(activity);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
    }

    private void initGoogleApiClientWithOptions(AppCompatActivity activity) {
        Logger.d(TAG, "initGoogleApiClientWithOptions :: start");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestId()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage(activity, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Logger.e(TAG, "initGoogleApiClientWithOptions :: failed " + connectionResult.getErrorMessage());
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        Logger.d(TAG, "handleGoogleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            if (Validator.isObjectValid(acct)) {
                User user = new User();
                user.setProfilePictureURL(acct.getPhotoUrl().toString());
                user.setName(acct.getDisplayName());
                notifyLogInSuccess(GOOGLE, user);
            }
        } else {

        }
    }

    private void signOutFromGoogleAccount() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Logger.d(TAG, "signOutFromGoogleAccount :: " + status.getStatusMessage());
                    }
                });
    }

    /**********************************************************************************************/
    /*******************************************  VK  *********************************************/
    /**********************************************************************************************/

    private void signInViaVK(AppCompatActivity activity) {
        VKSdk.login(activity);
    }

    private void handleVKSignInResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Logger.d(TAG, res.userId);
                getVkUserInfo(res.userId);
            }

            @Override
            public void onError(VKError error) {
                if (Validator.isObjectValid(error)) {
                    if (Validator.isStringValid(error.errorMessage)) {
                        Logger.e(TAG, error.errorMessage);
                    } else if (Validator.isStringValid(error.errorReason)) {
                        Logger.e(TAG, error.errorReason);
                    }
                }
            }
        })) ;
    }

    private void getVkUserInfo(final String usID) {
        VKParameters parameters = new VKParameters();
        parameters.put(VKApiConst.USER_ID, usID);
        parameters.put(VKApiConst.FIELDS, "photo_100");
        VKRequest vkRequest = VKApi.users().get(parameters);

        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                if (Validator.isObjectValid(response)) {
                    List<VKApiUser> userList = (List<VKApiUser>) response.parsedModel;

                    if(!Validator.isListValid(userList)){
                        notifyLogInError(VK);
                        return;
                    }

                    VKApiUser user = userList.get(0);

                    if(!Validator.isObjectValid(user)){
                        notifyLogInError(VK);
                        return;
                    }

                    User currentUser = new User();
                    currentUser.setName(user.first_name + " "+ user.last_name);
                    currentUser.setProfilePictureURL(user.photo_100);
                    notifyLogInSuccess(VK,currentUser);

                }
            }
                @Override
                public void onError (VKError error){
                    super.onError(error);
                    notifyLogInError(VK);
                }
            }

            );
        }

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
