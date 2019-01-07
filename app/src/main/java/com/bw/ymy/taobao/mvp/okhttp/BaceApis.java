package com.bw.ymy.taobao.mvp.okhttp;

import com.bw.ymy.taobao.Api.Api;

import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface BaceApis<T> {
    //get
    @GET
    Observable<ResponseBody> get(@Url String url);

    //post
    @POST
    Observable<ResponseBody> post(@Url String url, @QueryMap Map<String,String> map);
}
