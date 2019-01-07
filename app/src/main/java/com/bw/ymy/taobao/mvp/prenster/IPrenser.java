package com.bw.ymy.taobao.mvp.prenster;

import java.util.Map;

public interface IPrenser {
    //post
    void getRequest(String url, Map<String, String> map, Class clazz);
        //get
    void getData(String url,  Class clazz);
}
