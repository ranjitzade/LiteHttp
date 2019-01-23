package com.ranjitzade.litehttp.lib.core;

/**
 * Created by ranjit
 */
public interface IBaseLoader extends Runnable {
    void execute();

    boolean checkParameters();
}