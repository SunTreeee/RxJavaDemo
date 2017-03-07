package com.sun.myone.rxdemo;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * Created by Sun
 * on 2017/3/6.
 */

public interface BaiDuService {
    @GET("/")
    Flowable<ResponseBody> getText();
}
