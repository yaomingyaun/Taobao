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

public class FashionAdapter extends RecyclerView.Adapter<FashionAdapter.ViewHolder>{

    private List<HomeBean.ResultBean.MlssBean.CommodityListBeanXX> mdata=new ArrayList<>();
    private Context mcontext;

    public FashionAdapter(Context context) {
        this.mcontext = context;
    }
//加载数据
    public void setlist(List<HomeBean.ResultBean.MlssBean.CommodityListBeanXX> datas) {
        this.mdata=datas;
        notifyDataSetChanged();
    }
    public  int getcommodityId(int position)
    {
        return  mdata.get(position).getCommodityId();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(mcontext,R.layout.fanshion_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.title.setText(mdata.get(i).getCommodityName());
        viewHolder.price.setText(mdata.get(i).getPrice()+"");
        Glide.with(mcontext).load(mdata.get(i).getMasterPic()).into(viewHolder.icon);
        //点击
        viewHolder.fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clicklistener!=null)
                {
                     clicklistener.onClick(i);
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
        private LinearLayout fashion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.fashion_icon);
            title=itemView.findViewById(R.id.fashion_title);
            price=itemView.findViewById(R.id.fashion_price);
            fashion=itemView.findViewById(R.id.fashion);
        }
    }

    public   Clicklistener clicklistener;

    public   void  setOnclick(Clicklistener onclick)
    {
        clicklistener=onclick;
    }
    public interface Clicklistener
    {
        void  onClick(int postion);
    }
}
