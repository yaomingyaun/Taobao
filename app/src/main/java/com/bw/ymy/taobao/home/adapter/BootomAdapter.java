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
import com.bw.ymy.taobao.home.bean.BootomBean;
import com.bw.ymy.taobao.home.bean.TopBean;

import java.util.ArrayList;
import java.util.List;

public class BootomAdapter extends RecyclerView.Adapter<BootomAdapter.ViewHolder>{

    private List<BootomBean.ResultBean> mdata;
    private Context context;

    public BootomAdapter(Context context) {
        this.context = context;
        mdata=new ArrayList<>();
    }
    public  void  setlist(List<BootomBean.ResultBean> datas)
    {
        mdata.clear();
        mdata.addAll(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.popu_bootom_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.bootom_text.setText(mdata.get(i).getName());
        viewHolder.bootom_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lisetner!=null)
                {
                    lisetner.onclick(i);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder{
        private TextView bootom_text;
        private LinearLayout bootom_linear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bootom_text=itemView.findViewById(R.id.bootom_text);
            bootom_linear=itemView.findViewById(R.id.bootom_linear);

        }
    }

    public  interface  setSetOnclick
    {
        void  onclick(int index);
    }

    public  setSetOnclick lisetner;

    public  void  setLisetner(setSetOnclick setSetOnclick)
    {
        this. lisetner=setSetOnclick;
    }



}
