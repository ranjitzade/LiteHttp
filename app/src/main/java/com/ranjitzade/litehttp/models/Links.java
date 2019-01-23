package com.ranjitzade.litehttp.models;

import com.google.gson.annotations.SerializedName;
/**
 * @author ranjit
 */
public class Links {
    @SerializedName("self")
    public String self;
    @SerializedName("html")
    public String html;
    @SerializedName("photos")
    public String photos;
    @SerializedName("likes")
    public String likes;
    @SerializedName("download")
    public String download;
}