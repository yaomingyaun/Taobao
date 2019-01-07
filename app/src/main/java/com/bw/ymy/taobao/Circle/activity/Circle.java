package com.bw.ymy.taobao.Circle.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.ymy.taobao.Api.Api;
import com.bw.ymy.taobao.Circle.adapter.MyBase;
import com.bw.ymy.taobao.Circle.activity.bean.CircleBean;
import com.bw.ymy.taobao.R;
import com.bw.ymy.taobao.mvp.prenster.IPrenserlpl;
import com.bw.ymy.taobao.mvp.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

//朋友圈
public class Circle extends Fragment implements IView {
    private RecyclerView circle_recyclerview;
    private IPrenserlpl iPrenserlpl;
    private MyBase adapter1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.action_circle,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        circle_recyclerview = view.findViewById(R.id.circle_recyclerview);

        iPrenserlpl = new IPrenserlpl(this);

        //布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置方向
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置布局管理
        circle_recyclerview.setLayoutManager(layoutManager);
        //适配器
        lodata();
        adapter1 = new MyBase(getContext());
        circle_recyclerview.setAdapter(adapter1);
    }

    //加载数据
    public  void  lodata()
    {

        iPrenserlpl.getData(Api.quznzi,CircleBean.class);
    }

    @Override
    public void onsuccess(Object data) {
        if(data instanceof CircleBean)
        {
            CircleBean bean= (CircleBean) data;

               adapter1.setlist(bean.getResult());

        }
    }
}
