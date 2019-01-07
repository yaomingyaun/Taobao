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

public class SellAdpter extends RecyclerView.Adapter<SellAdpter.ViewHolder>{

    private List<HomeBean.ResultBean.RxxpBean.CommodityListBean> mdata=new ArrayList<>();
    private Context mcontext;

    public SellAdpter(Context context) {
        this.mcontext = context;
    }
//加载数据
    public void setlist(List<HomeBean.ResultBean.RxxpBean.CommodityListBean> datas) {
        this.mdata=datas;
        notifyDataSetChanged();
    }
    public  int getid(int position)
    {
        return  mdata.get(position).getCommodityId();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(mcontext,R.layout.sellshoping_item,null);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.title.setText(mdata.get(i).getCommodityName());
        Glide.with(mcontext).load(mdata.get(i).getMasterPic()).into(viewHolder.icon);

        viewHolder.sell_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listenter!=null)
                {
                    listenter.onClick(i);
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
        private TextView title;
        private LinearLayout sell_linear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.sell_icon);
            title=itemView.findViewById(R.id.sell_title);
            sell_linear=itemView.findViewById(R.id.sell_linear);
        }
    }
    //点击监听
    public ClickListenter listenter;

    public void setOnClickListenter(ClickListenter clickListenter){
        listenter=clickListenter;
    }

    public interface ClickListenter{
        void onClick(int position);
    }
}
