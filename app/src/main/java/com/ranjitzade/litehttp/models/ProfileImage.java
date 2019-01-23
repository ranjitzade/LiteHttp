package com.ranjitzade.litehttp.models;

import com.google.gson.annotations.SerializedName;
/**
 * @author ranjit
 */
public class ProfileImage {
    @SerializedName("small")
    public String small;
    @SerializedName("medium")
    public String medium;
    @SerializedName("large")
    public String large;
}