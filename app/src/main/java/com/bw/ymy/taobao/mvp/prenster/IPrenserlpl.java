package com.bw.ymy.taobao.mvp.prenster;


import com.bw.ymy.taobao.mvp.model.IModellpl;
import com.bw.ymy.taobao.mvp.mycalllback.MyCallBack;
import com.bw.ymy.taobao.mvp.view.IView;

import java.util.Map;
public class IPrenserlpl implements IPrenser {
    private IView iView;
    private IModellpl iModellpl;

    public IPrenserlpl(IView iView) {
        this.iView = iView;
        iModellpl=new IModellpl();
    }
    @Override
    public void getRequest(String url, Map<String, String> map, Class clazz) {
        iModellpl.getRequest(url, map, clazz, new MyCallBack() {
            @Override
            public void onsuccess(Object data) {
                iView.onsuccess(data); }

            @Override
            public void onFail(String error) {
                iView.onsuccess(error);
            }
        });
    }
    //get
    @Override
    public void getData(String url, Class clazz) {
        iModellpl.getData(url, clazz, new MyCallBack() {
            @Override
            public void onsuccess(Object data) {
                iView.onsuccess(data);
            }

            @Override
            public void onFail(String error) {
                iView.onsuccess(error);

            }
        });

    }




    //mvp解绑
    public  void detach()
    {
        iModellpl=null;
        iView=null;
    }
}
