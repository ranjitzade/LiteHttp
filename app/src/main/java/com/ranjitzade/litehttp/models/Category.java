package com.ranjitzade.litehttp.models;

import com.google.gson.annotations.SerializedName;
/**
 * @author ranjit
 */
public class Category {
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("photo_count")
    public int photoCount;
    @SerializedName("links")
    public Links links;
}