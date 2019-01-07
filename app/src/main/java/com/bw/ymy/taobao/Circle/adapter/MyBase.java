package com.bw.ymy.taobao.Circle.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.ymy.taobao.Circle.activity.bean.CircleBean;
import com.bw.ymy.taobao.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

/**
 * 圈子的adapter
 * */
public class MyBase extends RecyclerView.Adapter<MyBase.ViewHolder> {
    private List<CircleBean.ResultBean> mdata=new ArrayList<>();
    private Context mcontext;

    public MyBase(Context context) {
    this. mcontext = context;

    }
    public  void  setlist(List<CircleBean.ResultBean> datas)
    {
        this.mdata=datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyBase.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(mcontext,R.layout.circle_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //描述
        viewHolder.title.setText(mdata.get(i).getContent());
        //名字
        viewHolder.cicle_name.setText((mdata.get(i).getNickName()));

        Glide.with(mcontext).load(mdata.get(i).getImage()).into(viewHolder.icon2);

        //Glide.with(mcontext).load(mdata.get(i).getHeadPic()).into(viewHolder.simpleDraweeView);

        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(mcontext.getResources())
                //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
                .setRoundingParams(RoundingParams.asCircle())

                //构建
                .build();
        viewHolder.simpleDraweeView.setHierarchy(hierarchy);

        viewHolder.simpleDraweeView.setImageURI(Uri.parse(mdata.get(i).getHeadPic()));
        //日期
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(mdata.get(i).getCreateTime()));
        viewHolder.data.setText(date);

    }
    @Override
    public int getItemCount() {
        return mdata.size();
    }

      class ViewHolder extends RecyclerView.ViewHolder
    {
        private    TextView title,data,cicle_name;
        private ImageView icon2;
        private SimpleDraweeView simpleDraweeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             title=itemView.findViewById(R.id.cicle_title);
              data=itemView.findViewById(R.id.cicle_data);
            cicle_name=itemView.findViewById(R.id.cicle_name);
            simpleDraweeView=itemView.findViewById(R.id.cicle_con1);
            icon2=itemView.findViewById(R.id.cicle_icon2);
        }
    }
}
