package com.www.funone.api;

/**
 * Responsible for management of all the app's requests.
 * Also manages app's data here.
 */
public class DataManager {

    private RetrofitRequest call;

    public DataManager(RetrofitRequest retrofitRequest) {
        this.call = retrofitRequest;
    }

}
