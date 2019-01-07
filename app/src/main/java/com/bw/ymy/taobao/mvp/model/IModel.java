package com.bw.ymy.taobao.mvp.model;


import com.bw.ymy.taobao.mvp.mycalllback.MyCallBack;

import java.util.Map;

public interface IModel {
    //post
    void  getRequest(String urlstr, Map<String, String> map, Class clazz, MyCallBack callBack);
    //get
    void getData(String urlstr,Class clazz, MyCallBack callBack);

}
