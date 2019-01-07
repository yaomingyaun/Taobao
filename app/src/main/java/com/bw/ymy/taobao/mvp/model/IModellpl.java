package com.bw.ymy.taobao.mvp.model;


import com.bw.ymy.taobao.mvp.mycalllback.MyCallBack;
import com.bw.ymy.taobao.mvp.mycalllback.ICallBack;
import com.bw.ymy.taobao.mvp.okhttp.OkHttpNutils;
import com.bw.ymy.taobao.mvp.okhttp.Retorfit;
import com.google.gson.Gson;

import java.util.Map;

public class IModellpl implements IModel {

    //post
    @Override
    public void getRequest(String urlstr, Map<String, String> map, final Class clazz, final MyCallBack callBack) {

        //post  json  解析
        Retorfit.getInstance().post(urlstr, map, new Retorfit.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o=new Gson().fromJson(data,clazz);
                    if(callBack!=null)
                    {
                        callBack.onsuccess(o);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                    if(callBack!=null)
                    {
                        callBack.onFail(e.getMessage());
                    }
                }

            }

            @Override
            public void onFail(String error) {
                if(callBack!=null)
                {
                    callBack.onFail(error);
                }

            }
        });


    }

    //get
    @Override
    public void getData(String urlstr, final Class clazz, final MyCallBack callBack) {

       Retorfit.getInstance().get(urlstr, new Retorfit.HttpListener() {
           @Override
           public void onSuccess(String data) {
               Object o=new Gson().fromJson(data,clazz);
               if(callBack!=null)
               {
                   callBack.onsuccess(o);
               }
           }

           @Override
           public void onFail(String error) {
               if(callBack!=null)
               {
                   callBack.onFail(error);
               }
           }
       });


    }
}
