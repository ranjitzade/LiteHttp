package com.ranjitzade.litehttp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author ranjit
 */
public class Pinterest {
    @SerializedName("id")
    public String id;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("width")
    public int width;
    @SerializedName("height")
    public int height;
    @SerializedName("color")
    public String color;
    @SerializedName("likes")
    public int likes;
    @SerializedName("liked_by_user")
    public boolean likedByUser;
    @SerializedName("user")
    public User user;
    @SerializedName("urls")
    public Urls urls;
    @SerializedName("categories")
    public List<Category> categories = null;
    @SerializedName("links")
    public Links links;
}