package com.www.funone.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vitaliy.herasymchuk on 6/29/16.
 */
public class User implements Parcelable {

    private String name;
    private String id;
    private String profilePictureURL;

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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", profilePictureURL='" + profilePictureURL + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeString(this.profilePictureURL);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.profilePictureURL = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
