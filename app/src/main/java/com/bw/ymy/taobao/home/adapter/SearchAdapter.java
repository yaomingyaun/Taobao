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
import com.bw.ymy.taobao.home.bean.SearchBean;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<SearchBean.ResultBean> mdata;
    private Context context;

    public SearchAdapter(Context context) {
        this.context = context;
        mdata=new ArrayList<>();
    }
    ////刷新更多
    public  void  setlist( List<SearchBean.ResultBean> datas)
    {
        mdata.clear();
        mdata.addAll(datas);
        notifyDataSetChanged();
    }
    //加载更多
    public  void  addlist( List<SearchBean.ResultBean> datas)
    {

        mdata.addAll(datas);
        notifyDataSetChanged();
    }

    //获取布局
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.search_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        SearchBean.ResultBean bean=mdata.get(i);

        ViewHolder viewHolder1= (ViewHolder) viewHolder;
        //商品名字
        viewHolder1.title.setText(bean.getCommodityName());
        //商品价格
        viewHolder1.price.setText("￥"+bean.getPrice()+"");
        //销售
        viewHolder1.num.setText("已售"+bean.getSaleNum()+"");
        //图片
        Glide.with(context).load(bean.getMasterPic()).into(viewHolder1.icon);
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView title,price,num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.search_icon);
            title=itemView.findViewById(R.id.search_title);
            price=itemView.findViewById(R.id.search_price);
            num=itemView.findViewById(R.id.search_num);
        }
    }
}
