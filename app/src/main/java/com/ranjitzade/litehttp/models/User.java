package com.ranjitzade.litehttp.models;

import com.google.gson.annotations.SerializedName;

/**
 * @author ranjit
 */
public class User {
    @SerializedName("id")
    public String id;
    @SerializedName("username")
    public String username;
    @SerializedName("name")
    public String name;
    @SerializedName("profile_image")
    public ProfileImage profileImage;
    @SerializedName("links")
    public Links links;
}