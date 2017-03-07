package com.sun.myone.rxdemo;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by Sun
 * on 2017/3/6.
 */

public interface ResponseListener {
    void onSucceed(ResponseBody responseBody) throws IOException;
    void onError();
}
