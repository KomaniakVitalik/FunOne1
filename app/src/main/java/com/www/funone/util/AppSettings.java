package com.www.funone.util;

/**
 * Created by vitaliy.komaniak on 6/27/16.
 */
public class AppSettings {

    private boolean isAutoPlay;
    private boolean enableNotifications;
    private boolean facebook;
    private boolean google;
    private boolean vk;

    public boolean isAutoPlay() {
        return isAutoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
    }

    public boolean isEnableNotifications() {
        return enableNotifications;
    }

    public void setEnableNotifications(boolean enableNotifications) {
        this.enableNotifications = enableNotifications;
    }

    public boolean isFacebook() {
        return facebook;
    }

    public void setFacebook(boolean facebook) {
        this.facebook = facebook;
    }

    public boolean isGoogle() {
        return google;
    }

    public void setGoogle(boolean google) {
        this.google = google;
    }

    public boolean isVk() {
        return vk;
    }

    public void setVk(boolean vk) {
        this.vk = vk;
    }
}
