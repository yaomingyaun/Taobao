package com.bw.ymy.taobao.mvp.mycalllback;

public interface MyCallBack<T> {
    void onsuccess(T data);
    void  onFail(String error);
}
