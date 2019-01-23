package com.ranjitzade.litehttp.models;

import com.google.gson.annotations.SerializedName;
/**
 * @author ranjit
 */
public class Urls {
    @SerializedName("raw")
    public String raw;
    @SerializedName("full")
    public String full;
    @SerializedName("regular")
    public String regular;
    @SerializedName("small")
    public String small;
    @SerializedName("thumb")
    public String thumb;
}