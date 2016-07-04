package com.www.funone.util;

import com.www.funone.model.User;

import io.realm.RealmObject;

/**
 * Created by vitaliy.komaniak on 6/27/16.
 */
public class AppSettings extends RealmObject {

    private int id;
    private boolean isAutoPlay;
    private boolean enableNotifications;
    private boolean facebook;
    private boolean google;
    private boolean vk;

    private User user;

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
        if(Validator.isObjectValid(user)){
            return user.isFacebook();
        }
        return facebook;
    }

    public void setFacebook(boolean facebook) {
        this.facebook = facebook;
    }

    public boolean isGoogle() {
        if(Validator.isObjectValid(user)){
            return user.isGoogle();
        }
        return google;
    }

    public void setGoogle(boolean google) {
        this.google = google;
    }

    public boolean isVk() {
        if(Validator.isObjectValid(user)){
            return user.isVk();
        }
        return vk;
    }

    public void setVk(boolean vk) {
        this.vk = vk;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppSettings that = (AppSettings) o;

        if (isAutoPlay != that.isAutoPlay) return false;
        if (enableNotifications != that.enableNotifications) return false;
        if (facebook != that.facebook) return false;
        if (google != that.google) return false;
        if (vk != that.vk) return false;
        return user != null ? user.equals(that.user) : that.user == null;

    }

    @Override
    public int hashCode() {
        int result = (isAutoPlay ? 1 : 0);
        result = 31 * result + (enableNotifications ? 1 : 0);
        result = 31 * result + (facebook ? 1 : 0);
        result = 31 * result + (google ? 1 : 0);
        result = 31 * result + (vk ? 1 : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppSettings{" +
                "isAutoPlay=" + isAutoPlay +
                ", enableNotifications=" + enableNotifications +
                ", facebook=" + facebook +
                ", google=" + google +
                ", vk=" + vk +
                ", user=" + user +
                '}';
    }

}
