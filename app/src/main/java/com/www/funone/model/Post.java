package com.www.funone.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vitaliy.herasymchuk on 6/27/16.
 */
public class Post implements Parcelable {

    private boolean liked;

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.liked ? (byte) 1 : (byte) 0);
    }

    public Post() {
    }

    protected Post(Parcel in) {
        this.liked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
