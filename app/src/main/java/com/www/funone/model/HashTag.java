package com.www.funone.model;

/**
 * Created by vitaliy.herasymchuk on 6/26/16.
 */
public class HashTag {
    private String name;
    private int postsNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPostsNumber() {
        return postsNumber;
    }

    public void setPostsNumber(int postsNumber) {
        this.postsNumber = postsNumber;
    }

    @Override
    public String toString() {
        return "HashTag{" +
                "name='" + name + '\'' +
                ", postsNumber=" + postsNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HashTag hashTag = (HashTag) o;

        if (postsNumber != hashTag.postsNumber) return false;
        return name != null ? name.equals(hashTag.name) : hashTag.name == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + postsNumber;
        return result;
    }
}
