package com.bw.ymy.taobao.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.ymy.taobao.R;
import com.bw.ymy.taobao.home.bean.ParticularsBean;
import com.bw.ymy.taobao.mvp.prenster.IPrenserlpl;
import com.bw.ymy.taobao.mvp.view.IView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

public class ParticularsBeanActivity extends AppCompatActivity implements IView {

    private Banner banner;
    private int pid;
    private IPrenserlpl iPrenserlpl;
    private TextView pa_title,pa_num,pa_price;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulars);
        //获取资源id
        banner=findViewById(R.id.sell_banner);
    webView=findViewById(R.id.webview);
        pa_title=findViewById(R.id.pa_title);
        pa_num=findViewById(R.id.pa_num);
        pa_price=findViewById(R.id.pa_price);

        iPrenserlpl=new IPrenserlpl(this);


        //接受传值
        Intent intent=getIntent();
        String pids=intent.getStringExtra("pid");
        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView=new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }
        });
        //详情
            String pra="http://172.17.8.100/small/commodity/v1/findCommodityDetailsById?userid=18&ssessionid=15320748258726&commodityId="+pids+"";
         iPrenserlpl.getData(pra,ParticularsBean.class);

    }

    @Override
    public void onsuccess(Object data) {
        List<String> list=new ArrayList<>();
        ParticularsBean particularsBean= (ParticularsBean) data;
        String[] split=particularsBean.getResult().getPicture().split("\\,");
        for (int i=0;i<split.length;i++)
        {
            list.add(split[i]);
        }
        banner.setImages(list);
        //轮播图开始
        banner.start();
        //显示商品名称
        pa_title.setText(particularsBean.getResult().getCommodityName());
        //显示商品数量
        pa_num.setText("已售"+particularsBean.getResult().getSaleNum()+"");
        //显示商品价格
        pa_price.setText("价格："+particularsBean.getResult().getPrice()+"");

        webView.loadDataWithBaseURL(null,particularsBean.getResult().getDetails(),"text/html","utf-8",null);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //System.exit(0);
        if(webView!=null) {
            webView.setVisibility(View.GONE);
            webView.removeAllViews();
            webView.destroy();

        }
    }

}
