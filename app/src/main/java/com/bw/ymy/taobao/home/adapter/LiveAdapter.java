package com.bw.ymy.taobao.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.ymy.taobao.R;
import com.bw.ymy.taobao.home.bean.HomeBean;

import java.util.ArrayList;
import java.util.List;

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.ViewHolder>{

    private List<HomeBean.ResultBean.PzshBean.CommodityListBeanX> mdata=new ArrayList<>();
    private Context mcontext;

    public LiveAdapter(Context context) {
        this.mcontext = context;
    }
//加载数据
    public void setlist(List<HomeBean.ResultBean.PzshBean.CommodityListBeanX> datas) {
        this.mdata=datas;
        notifyDataSetChanged();
    }
            //获取id
    public int getcommodityI(int position)
    {
        return mdata.get(position).getCommodityId();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(mcontext,R.layout.live_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.title.setText(mdata.get(i).getCommodityName());
        viewHolder.price.setText(mdata.get(i).getPrice()+"");
        Glide.with(mcontext).load(mdata.get(i).getMasterPic()).into(viewHolder.icon);
        viewHolder.live_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listenere!=null)
                {
                    listenere.Onclick(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView icon;
        private TextView title,price;
        private LinearLayout live_linear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.live_icon);
            title=itemView.findViewById(R.id.live_title);
            price=itemView.findViewById(R.id.live_price);
            live_linear=itemView.findViewById(R.id.live_linear);
        }
    }

    public  interface  OnclickListenere{
        void  Onclick(int position);
    }

    public OnclickListenere listenere;

    public void   setOnclickListenere(OnclickListenere onclickListenere)
    {
        listenere=onclickListenere;
    }

}
