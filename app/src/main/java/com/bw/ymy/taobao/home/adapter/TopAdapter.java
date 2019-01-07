package com.bw.ymy.taobao.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.ymy.taobao.R;
import com.bw.ymy.taobao.home.bean.TopBean;

import java.util.ArrayList;
import java.util.List;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.ViewHolder>{

    private List<TopBean.ResultBean> mdata;
    private Context context;

    public TopAdapter(Context context) {
        this.context = context;
        mdata=new ArrayList<>();
    }
    public  void  setlist(List<TopBean.ResultBean> datas)
    {
        mdata.clear();
       if(mdata!=null)
       {
           mdata.addAll(datas);
       }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view=LayoutInflater.from(context).inflate(R.layout.popu_top_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.top_text.setText(mdata.get(i).getName());

        viewHolder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                {
                    listener.onClick(i);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder{
        private TextView top_text;
        private LinearLayout linear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            top_text=itemView.findViewById(R.id.top_text);
            linear=itemView.findViewById(R.id.top_linear);

        }
    }

    public  setOnclickListener listener;
    public  void  setListener(setOnclickListener onclickListener)
    {
        this.listener=onclickListener;
    }

    public  interface  setOnclickListener
    {
        void  onClick(int index);
    }
}
