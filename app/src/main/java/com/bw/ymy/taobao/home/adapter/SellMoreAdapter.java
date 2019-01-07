package com.bw.ymy.taobao.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.ymy.taobao.R;
import com.bw.ymy.taobao.home.bean.MoreBean;

import java.util.ArrayList;
import java.util.List;

public class SellMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MoreBean.ResultBean> mdata;
    private Context mcontext;

    public SellMoreAdapter(Context context) {
        this.mcontext = context;
        mdata=new ArrayList<>();
    }
    ////刷新更多
public  void  setlist(List<MoreBean.ResultBean> datas)
{
        mdata.clear();
        mdata.addAll(datas);
        notifyDataSetChanged();
}
//加载更多
    public  void  addlist(List<MoreBean.ResultBean> datas)
    {

        mdata.addAll(datas);
        notifyDataSetChanged();
    }
    //获取布局
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.more_item,null);
        return new ViewHolder(view);
    }

    //绑定
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MoreBean.ResultBean bean=mdata.get(i);

        ViewHolder viewHolder1= (ViewHolder) viewHolder;
        viewHolder1.title.setText(bean.getCommodityName());
        viewHolder1.price.setText("￥"+bean.getPrice()+"");
        Glide.with(mcontext).load(mdata.get(i).getMasterPic()).into(viewHolder1.icon);
    }
    @Override
    public int getItemCount() {
        return mdata.size();
    }

    //获取资源id
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView title,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.more_icon);
            title=itemView.findViewById(R.id.more_title);
            price=itemView.findViewById(R.id.more_price);
        }
    }
}
