package com.www.funone.model;


import io.realm.RealmObject;


/**
 * Created by vitaliy.herasymchuk on 6/29/16.
 */
public class User extends RealmObject {

    private int serverId = 1;
    private String id;

    private String name = "";
    private String profilePictureURL;

    private boolean facebook;
    private boolean google;
    private boolean vk;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", profilePictureURL='" + profilePictureURL + '\'' +
                ", facebook=" + facebook +
                ", google=" + google +
                ", vk=" + vk +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (facebook != user.facebook) return false;
        if (google != user.google) return false;
        if (vk != user.vk) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        return profilePictureURL != null ? profilePictureURL.equals(user.profilePictureURL) : user.profilePictureURL == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (profilePictureURL != null ? profilePictureURL.hashCode() : 0);
        result = 31 * result + (facebook ? 1 : 0);
        result = 31 * result + (google ? 1 : 0);
        result = 31 * result + (vk ? 1 : 0);
        return result;
    }


}
