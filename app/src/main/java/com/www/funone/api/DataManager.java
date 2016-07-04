package com.www.funone.api;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.www.funone.R;
import com.www.funone.model.User;
import com.www.funone.util.AppSettings;
import com.www.funone.util.Logger;
import com.www.funone.util.Pref;
import com.www.funone.util.Validator;

import java.io.IOException;
import java.net.HttpURLConnection;

import io.realm.Realm;
import retrofit2.Response;

/**
 * Responsible for management of all the app's requests.
 * Also manages app's data here.
 */
public class DataManager {

    public static final String TAG = "DataManager";

    public static final String EMPTY_SERVER_MESSAGE = "";
    private static final int ERROR_NULL_RESPONSE_OBJECT = 1;

    private OnResponseListener mResponseListener;
    private RetrofitRequest request;

    private Realm DB = Realm.getDefaultInstance();

    public DataManager(RetrofitRequest retrofitRequest) {
        this.request = retrofitRequest;
        this.DB = Realm.getDefaultInstance();
        initAppSettings();
    }

    /**********************************************************************************************/
    /***************************************** SET UP *********************************************/
    /**********************************************************************************************/

    /**
     * Delivers response to listener, manages all the possible statuses of AppResponse object.
     * Generates response messages if failed.
     *
     * @param response - AppResponse
     */
    public boolean deliverResponse(Response<?> response, String requestTag) {
        if (Validator.isObjectValid(mResponseListener)) {
            if (Validator.isObjectValid(response)) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    mResponseListener.onResponseSuccess(response, requestTag);
                    return true;
                } else {
                    try {
                        String message = new Gson().fromJson(response.errorBody().string(), String.class);
                        if (Validator.isObjectValid(message)) {
                            notifyFailed(generateFailMessage(response.code()), message);
                        }
                    } catch (IOException | JsonSyntaxException | IllegalStateException e) {
                        notifyFailed(generateFailMessage(response.code()), EMPTY_SERVER_MESSAGE);
                        Logger.e("DataManager", e.toString());
                    }
                    return false;
                }
            } else {
                notifyFailed(ERROR_NULL_RESPONSE_OBJECT, EMPTY_SERVER_MESSAGE);
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Notifies that response has failed
     *
     * @param statusIntMessage    - resources id
     * @param statusStringMessage - string message
     */
    private void notifyFailed(int statusIntMessage, String statusStringMessage) {
        if (Validator.isObjectValid(mResponseListener)) {
            mResponseListener.onResponseFail(statusIntMessage, statusStringMessage);
        }
    }

    private void notifyNoNetwork() {
        if (Validator.isObjectValid(mResponseListener)) {
            mResponseListener.onNoNetwork();
        }
    }


    /**
     * Registers Response Listener
     *
     * @param listener - Listener
     */
    public void registerResponseListener(OnResponseListener listener) {
        this.mResponseListener = listener;
    }

    /**
     * Generates string resource identifier of response basing on status code.
     *
     * @param statusCode - int status code
     */
    private int generateFailMessage(int statusCode) {
        int failMsg = -1;
        switch (statusCode) {
            case HttpURLConnection.HTTP_BAD_REQUEST:
                failMsg = R.string.http_bad_request;
                break;
            case HttpURLConnection.HTTP_FORBIDDEN:
                failMsg = R.string.http_forbidden;
                break;
            case HttpURLConnection.HTTP_NOT_FOUND:
                failMsg = R.string.http_not_found;
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                failMsg = R.string.http_server_error;
                break;
        }
        return failMsg;
    }

    /**
     * Logs onFail callback
     *
     * @param throwable - Throwable
     */
    private void logException(Throwable throwable) {
        if (Validator.isObjectValid(throwable)) {
            Logger.e(TAG, throwable.toString());
        }
    }

    /**
     * Checks whether Throwable is of IOException - meaning that there's no Internet connection
     *
     * @param t - Throwable
     * @return - true if IOException
     */
    private boolean isNoNetworkException(Throwable t) {
        if (t instanceof IOException) {
            notifyNoNetwork();
            return true;
        } else {
            logException(t);
            return false;
        }
    }

    /**
     * Listener to deliver response to
     */
    public interface OnResponseListener {
        //Notifies response is success
        void onResponseSuccess(Response<?> response, String pendingRequestTag);

        //Notifies response failed
        void onResponseFail(int statusMessage, String serverMessage);

        //Notifies listener of No Internet issue
        void onNoNetwork();
    }

    /**********************************************************************************************/
    /**************************************** Requests ********************************************/
    /**********************************************************************************************/


    /**********************************************************************************************/
    /************************************** Local Requests ****************************************/
    /**********************************************************************************************/

    public void initAppSettings() {
        DB.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm.where(AppSettings.class).findFirst() == null) {
                    realm.createObject(AppSettings.class);
                    Logger.d(TAG, "initAppSettings :: initialized");
                }

            }
        });
    }

    public void saveOrUpdateUser(final User user) {
        DB.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Logger.d(TAG, "saveOrUpdateUser :: start");
                AppSettings appSettings = realm.where(AppSettings.class).findFirst();
                User appUser = null;

                if (Validator.isObjectValid(appSettings)) {
                    appUser = appSettings.getUser();
                }

                if (!Validator.isObjectValid(appUser)) {
                    appSettings.setUser(realm.copyToRealm(user));
                } else {
                    if (appUser.getServerId() != user.getServerId()) {
                        Logger.d(TAG, "saveOrUpdateUser :: replacing last user with new one");
                        appSettings.setUser(realm.copyToRealm(user));
                    } else {
                        Logger.d(TAG, "saveOrUpdateUser :: updating current user");
                        appUser.setProfilePictureURL(user.getProfilePictureURL());
                        appUser.setName(user.getName());
                        appUser.setGoogle(user.isGoogle());
                        appUser.setVk(user.isVk());
                        appUser.setFacebook(user.isFacebook());
                        appUser.setId(user.getId());
                    }
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Logger.d(TAG, "saveOrUpdateUser :: success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Logger.d(TAG, "saveOrUpdateUser :: error " + error.toString());
            }
        });
    }


    public AppSettings getAppSettings() {
        if (Validator.isObjectValid(DB.where(AppSettings.class).findFirst())) {
            return DB.where(AppSettings.class).findFirst();
        }
        return new AppSettings();
    }

//
//    public User getCurrentUser() {
//        return getAppSettings().getUser();
//    }

}
