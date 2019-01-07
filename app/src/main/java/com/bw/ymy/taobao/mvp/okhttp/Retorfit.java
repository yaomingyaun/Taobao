package com.bw.ymy.taobao.mvp.okhttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Retorfit {
    //这个是网址里面共有的
    private final String BASE_URL = "http://172.17.8.100/small/";

    private static  Retorfit retrofitManager;
    //单列
    public  static  synchronized  Retorfit getInstance()
    {
        if(retrofitManager==null)
        {
            retrofitManager=new Retorfit();
        }
        return  retrofitManager;
    }
    //拿到BaceApis
    private  BaceApis baceApis;
    //读写拦截器
    public  Retorfit()
    {
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.connectTimeout(15,TimeUnit.SECONDS);
        builder.readTimeout(15,TimeUnit.SECONDS);
        builder.writeTimeout(15,TimeUnit.SECONDS);
        builder.addInterceptor(httpLoggingInterceptor);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client=builder.build();

        Retrofit retrofit=new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
        baceApis=retrofit.create(BaceApis.class);

    }
    /**
     * 可以这样生成Map<String, RequestBody> requestBodyMap
     * Map<String, String> requestDataMap这里面放置上传数据的键值对。
     */

    public Map<String, RequestBody> generateRequestBody(Map<String,String> requestDataMap)
    {
        Map<String,RequestBody> requestBodyMap=new HashMap<>();
        for (String key:requestDataMap.keySet())
        {
            RequestBody requestBody
                    =RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key)==null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key,requestBody);
        }
        return  requestBodyMap;
    }

    //get请求

    public  void get(String url,HttpListener listener)
    {
        baceApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));

    }
    /**
     * 普通post请求
     */
    public Retorfit post(String url, Map<String, String> map,HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        baceApis.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
        return retrofitManager;
    }


    public Observer getObserver(final HttpListener listener)

    {
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onFail(e.getMessage());
                }

            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String string = responseBody.string();
                    if (listener != null) {
                        listener.onSuccess(string);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onFail(e.getMessage());
                    }
                }


            }

        };
        return observer;
    }


    public HttpListener listener;
    public void setHttpListener(HttpListener httpListener){
        this.listener = httpListener;
    }

    public interface HttpListener {
        void onSuccess(String data);
        void onFail(String error);
    }
}
