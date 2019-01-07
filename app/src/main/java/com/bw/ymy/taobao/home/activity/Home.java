package com.bw.ymy.taobao.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.ymy.taobao.Api.Api;
import com.bw.ymy.taobao.R;
import com.bw.ymy.taobao.home.adapter.BootomAdapter;
import com.bw.ymy.taobao.home.adapter.ClassAdapter;
import com.bw.ymy.taobao.home.adapter.FashionAdapter;
import com.bw.ymy.taobao.home.adapter.LiveAdapter;
import com.bw.ymy.taobao.home.adapter.SearchAdapter;
import com.bw.ymy.taobao.home.adapter.SellAdpter;
import com.bw.ymy.taobao.home.adapter.SellMoreAdapter;
import com.bw.ymy.taobao.home.adapter.TopAdapter;
import com.bw.ymy.taobao.home.bean.BootomBean;
import com.bw.ymy.taobao.home.bean.ClassifyBean;
import com.bw.ymy.taobao.home.bean.HomeBean;
import com.bw.ymy.taobao.home.bean.MoreBean;
import com.bw.ymy.taobao.home.bean.SearchBean;
import com.bw.ymy.taobao.home.bean.TopBean;
import com.bw.ymy.taobao.home.bean.XBannerBean;
import com.bw.ymy.taobao.mvp.prenster.IPrenserlpl;
import com.bw.ymy.taobao.mvp.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import butterknife.BindView;
import butterknife.ButterKnife;

//主页
public class Home extends Fragment implements IView ,View.OnClickListener{

    //热销商品
    private RecyclerView sellrecyclerView;
    private SellAdpter sellAdpter;
    //魔丽时尚
    private RecyclerView  fashino;
    private FashionAdapter fashionAdapter;
    //品质生活
    private RecyclerView  live;
    private LiveAdapter liveAdapter;
    //搜索
    private EditText home_name;
    private  ImageView image_search;
    private TextView search_text;
    private LinearLayout linearLayout;
    private  XRecyclerView searchxrecview;
    private SearchAdapter searchAdapter;

    //点击更多
    private ImageView sell_image,live_image,fashion_image;
    private SellMoreAdapter sellMoreAdapter;
    private XRecyclerView sell_xrecyclerView;
    private  int page=1;
    String moreid;
    //从bean类吧值拿出来
    private  int ids;
    //点击切换 pp
    private  ImageView home_switch;
    private TopAdapter adapter;
    private BootomAdapter bootomAdapter;
    private PopupWindow popupWindow;
    private TopBean bb;
    private  BootomBean bootomBean;
    private RecyclerView topview;
    private  XRecyclerView bootom_class_xrecyclerView;
    private ClassAdapter classAdapter;


   @BindView(R.id.banner)
    XBanner xBanner;

    private IPrenserlpl iPrenserlpl;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //注册
        ButterKnife.bind(this,view);
        //实例化
        iPrenserlpl=new IPrenserlpl(this);
        //获取资源id
        sellrecyclerView=view.findViewById(R.id.sellrecview);
        fashino=view.findViewById(R.id.fashion);
        live=view.findViewById(R.id.liverecyclerView);
        home_switch=view.findViewById(R.id.home_switch);
        //bootomXre
        bootom_class_xrecyclerView=view.findViewById(R.id.bootom_class_xrecyclerView);
        //点击搜素搜索
        home_name=view.findViewById(R.id.home_name);
        //点击搜索
        search_text=view.findViewById(R.id.search_text);
        //搜索圆圈
        image_search=view.findViewById(R.id.image_search);
        //大布局
        linearLayout=view.findViewById(R.id.linear);
        //点击搜索圆圈
        searchxrecview=view.findViewById(R.id.search_recycleview);

        //获取资源id 点击三个小圆圈 加载更多
        sell_image=view.findViewById(R.id.sell_image);
        live_image=view.findViewById(R.id.live_image);
        fashion_image=view.findViewById(R.id.fashion_image);
        sell_xrecyclerView=view.findViewById(R.id.sell_xrecyclerView);

        //点击事件切换布局二级列表
        home_switch.setOnClickListener(this);
        //点击搜索圆圈消失
        image_search.setOnClickListener(this);
        //点击文字开始搜索
        search_text.setOnClickListener(this);
        //点击查看更多 热销
        sell_image.setOnClickListener(this);
        //查看更多 魔丽时尚
        fashion_image.setOnClickListener(this);
        //查看更多 生活
        live_image.setOnClickListener(this);
        //热销商品
        sellmanger();
        //魔丽时尚
        fashional();
        //品质时尚
        live();
        //网络xbanner
       iPrenserlpl.getData(Api.xbanner,XBannerBean.class);

        ////热销商品点击进入详情页面
        sellAdpter.setOnClickListenter(new SellAdpter.ClickListenter() {
            @Override
            public void onClick(int position) {
                int pid=sellAdpter.getid(position);
                Intent integer=new Intent(getActivity(),ParticularsBeanActivity.class);
                integer.putExtra("pid",pid+"");
                startActivity(integer);
            }
        });
        ////时尚生活点击进入详情页面
       fashionAdapter.setOnclick(new FashionAdapter.Clicklistener() {
           @Override
           public void onClick(int postion) {
               int pid=fashionAdapter.getcommodityId(postion);
               Intent integer=new Intent(getActivity(),ParticularsBeanActivity.class);
               integer.putExtra("pid",pid+"");
               startActivity(integer);
           }
       });
        ////生活品质点击进入详情页面
       liveAdapter.setOnclickListenere(new LiveAdapter.OnclickListenere() {
           @Override
           public void Onclick(int position) {
               int pid=liveAdapter.getcommodityI(position);
               Intent integer=new Intent(getActivity(),ParticularsBeanActivity.class);
               integer.putExtra("pid",pid+"");
               startActivity(integer);
           }
       });

    }

    //点击事件
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            //点击弹出二级列表
            case R.id.home_switch:
                ppswitch();
                break;
                //点击搜索圆圈 出现搜索框
            case R.id.image_search:
                //输入框显示
                home_name.setVisibility(View.VISIBLE);
                //输入文字显示
                search_text.setVisibility(View.VISIBLE);
                //圆圈隐藏
                image_search.setVisibility(View.GONE);
                //主页面所有内容隐藏
                linearLayout.setVisibility(View.GONE);
                sell_xrecyclerView.setVisibility(View.GONE);
                break;
                //点击 输入框搜索的内容
            case R.id.search_text:
                //把搜索的内容显示
                searchxrecview.setVisibility(View.VISIBLE);
                searchtext();
                break;
                //点击查看更多
            case  R.id.sell_image:
                //把大布局隐藏
                linearLayout.setVisibility(View.GONE);
                //显示出来Xrec
                sell_xrecyclerView.setVisibility(View.VISIBLE);
                moreid="1002";
                lodata(moreid);
                sellmore();
                break;
            //点击查看更多
            case  R.id.fashion_image:
                //把大布局隐藏
                linearLayout.setVisibility(View.GONE);
                //显示出来Xrec
                sell_xrecyclerView.setVisibility(View.VISIBLE);
                moreid="1003";
                lodata(moreid);
                sellmore();
                break;
            //点击查看更多
            case  R.id.live_image:
                //把大布局隐藏
                linearLayout.setVisibility(View.GONE);
                //显示出来Xrec
                sell_xrecyclerView.setVisibility(View.VISIBLE);
                moreid="1004";
                lodata(moreid);
                sellmore();
                break;

                default:break;

        }

    }
    //品质生活
    private void live() {
        //布局管理
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        //设置方向   横向HORIZONTAL
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置布局管理
        live.setLayoutManager(layoutManager);
        //适配器
        liveAdapter=new LiveAdapter(getContext());
        live.setAdapter(liveAdapter);
        //网络
        iPrenserlpl.getData(Api.sell,HomeBean.class);
    }
    //魔丽时尚
    private void fashional() {
        //布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置方向   横向HORIZONTAL
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置布局管理
        fashino.setLayoutManager(layoutManager);
        //适配器
        fashionAdapter=new FashionAdapter(getContext());
        fashino.setAdapter(fashionAdapter);
        //网络
        iPrenserlpl.getData(Api.sell,HomeBean.class);
    }
    //热销商品
    private void sellmanger()
    {
        //布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置方向   横向HORIZONTAL
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //设置布局管理
        sellrecyclerView.setLayoutManager(layoutManager);
        //适配器
        sellAdpter=new SellAdpter(getContext());
        sellrecyclerView.setAdapter(sellAdpter);
        //网络
        iPrenserlpl.getData(Api.sell,HomeBean.class);
    }
    //点击二级列表
    public  void  ppswitch()
    {
        //一级条目    获取布局
        View v=View.inflate(getActivity(),R.layout.popu_home_item,null);
                 //加载布局top
          topview=v.findViewById(R.id.top_reviciew);
           adapter=new TopAdapter(getActivity());
           //创建布局管理
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        topview.setLayoutManager(layoutManager);
        //适配器
        topview.setAdapter(adapter);

        //设置条目之间的距离
      AppinfoiItemDecoration decoration = new AppinfoiItemDecoration();
       topview.addItemDecoration(decoration);
       //加载下面布局的 bootom
        RecyclerView bootomView=v.findViewById(R.id.bootm_reviciew);
        bootomAdapter=new BootomAdapter(getActivity());
        //布局
        LinearLayoutManager layoutManager1=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bootomView.setLayoutManager(layoutManager1);
        bootomView.setAdapter(bootomAdapter);
        bootomView.addItemDecoration(decoration);
        //设置popo
        popupWindow=new PopupWindow(v,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setFocusable(true);
        //设置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5d5d5d")));
        //设置可触摸
        popupWindow.setTouchable(true);
        //设置位置
        popupWindow.showAtLocation(v,Gravity.CENTER_VERTICAL,5,-450);
        pplodata();
        //点击一级列表，弹出二级列表
       adapter.setListener(new TopAdapter.setOnclickListener() {
            @Override
            public void onClick(int index) {
                //接受传值
                String  sid=bb.getResult().get(index).getId();
                iPrenserlpl.getData(String.format(Api.URL_FIND_SECOND_CATEGORY_GET,sid),BootomBean.class);
            }
        });
       pplodata();
       //点击二级联动出现分类的产品
        bootomAdapter.setLisetner(new BootomAdapter.setSetOnclick() {
            @Override
            public void onclick(int index) {

                //获取id  传值
                String idd=bootomBean.getResult().get(index).getId();

                //显示页面
                bootom_class_xrecyclerView.setVisibility(View.VISIBLE);
                //其余内容隐藏
                linearLayout.setVisibility(View.GONE);
                //布局
                GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
                gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
                bootom_class_xrecyclerView.setLayoutManager(gridLayoutManager);
                //适配器
                classAdapter=new ClassAdapter(getContext());
                bootom_class_xrecyclerView.setAdapter(classAdapter);
                //刷新
                bootom_class_xrecyclerView.setPullRefreshEnabled(true);
                bootom_class_xrecyclerView.setLoadingMoreEnabled(true);
                //回调
                bootom_class_xrecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        page++;
                        classlodata();
                        Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onLoadMore() {
                        classlodata();
                        Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_LONG).show();
                    }
                });
                //判断网络
                classlodata();
            }
        });

    }
    //把搜索的内容显示
    public  void  searchtext()
    {
        //布局
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        searchxrecview.setLayoutManager(gridLayoutManager);
        //适配器
        searchAdapter=new SearchAdapter(getContext());
        searchxrecview.setAdapter(searchAdapter);
        //上拉下拉
        searchxrecview.setPullRefreshEnabled(true);
        searchxrecview.setLoadingMoreEnabled(true);
        //回调
        searchxrecview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page++;
                searchlodata();
                Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoadMore() {
                searchlodata();
                Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_LONG).show();
            }
        });

        searchlodata();
    }
    //二级分类查看更多
    public  void classlodata()
    {
        String classid=bootomBean.getResult().get(0).getId();
        iPrenserlpl.getData(String.format(Api.Classsify,classid),ClassifyBean.class);
    }
    //popu  获取一级列表
    public  void  pplodata()
    {
        iPrenserlpl.getData(Api.URL_FIND_FIRST_CATEGORY_GET,TopBean.class);
    }

    //点击搜索内容
    private  void  searchlodata()
    {
            //获取输入搜索的内容
        String name=home_name.getText().toString();
       iPrenserlpl.getData(String.format(Api.search,name,page++),SearchBean.class);

    }

    //点击 热销，魔力…… 按钮 出现更多
    public  void  sellmore()
    {
        //网格布局
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        sell_xrecyclerView.setLayoutManager(gridLayoutManager);
        //适配器
        sellMoreAdapter=new SellMoreAdapter(getContext());
        sell_xrecyclerView.setAdapter(sellMoreAdapter);
        //上啦加载
        sell_xrecyclerView.setPullRefreshEnabled(true);
        //下拉刷新载
        sell_xrecyclerView.setLoadingMoreEnabled(true);
        //回调
        sell_xrecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                lodata(moreid);
                Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoadMore() {
                lodata(moreid);
                Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_LONG).show();
            }
        });
        lodata(moreid);
    }
    //点击更多
    private  void  lodata(String moreid)
    {
        //id传过来
        iPrenserlpl.getData(String.format(Api.more,moreid,page++),MoreBean.class);

    }


    //判断
    @Override
    public void onsuccess(Object data) {
        if(data instanceof HomeBean) {
            HomeBean bean= (HomeBean) data;
            //热销
            sellAdpter.setlist(bean.getResult().getRxxp().get(0).getCommodityList());
                //从bean类里面吧id获取出来
             ids=bean.getResult().getRxxp().get(0).getId();
            //时尚
            fashionAdapter.setlist(bean.getResult().getMlss().get(0).getCommodityList());
            //生活
            liveAdapter.setlist(bean.getResult().getPzsh().get(0).getCommodityList());
        }

        //XBanner,
        else  if(data instanceof XBannerBean)
        {
             XBannerBean banner1= (XBannerBean) data;
              xBanner.setData(banner1.getResult(),null);
             xBanner.loadImage(new XBanner.XBannerAdapter() {
               @Override
               public void loadBanner(XBanner banner, Object model, View view, int position) {
                   XBannerBean.ResultBean bean= (XBannerBean.ResultBean) model;
                   Glide.with(getActivity()).load(bean.getImageUrl()).into((ImageView) view);
                   //时间
                   banner.setPageChangeDuration(1000);
               }
           });
        }
        //点击更多
        else if(data instanceof MoreBean)
        {
            MoreBean bean1= (MoreBean) data;
            if(page==1)
            {

                sellMoreAdapter.setlist(bean1.getResult());
            }else
            {
                sellMoreAdapter.addlist(bean1.getResult());
            }
            page++;
            sell_xrecyclerView.refreshComplete();
            sell_xrecyclerView.loadMoreComplete();

        }
        //搜索的内容
        else if(data instanceof SearchBean)
        {
            SearchBean searchBean= (SearchBean) data;
            if(page==1)
            {
                searchAdapter.setlist(searchBean.getResult());
            }else
            {
                searchAdapter.addlist(searchBean.getResult());
            }
            page++;
            searchxrecview.refreshComplete();
            searchxrecview.loadMoreComplete();

        }
        //判断一级列表
        else  if(data instanceof  TopBean)
        {

             bb = (TopBean) data;



            if (bb == null || !bb.isSucess()) {
                Toast.makeText(getActivity(), bb.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                adapter.setlist(bb.getResult());
              //  sid=bb.getResult().get(0).getId();
            }

        }
        //判断二级列表
        else if (data instanceof BootomBean) {
             bootomBean = (BootomBean) data;
            if (bootomBean == null || !bootomBean.isSuccess()) {
                Toast.makeText(getActivity(), bootomBean.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                bootomAdapter.setlist(bootomBean.getResult());
            }
        }
        //判断二级列表更多内容
        else if(data instanceof ClassifyBean)
        {
            ClassifyBean classifyBean= (ClassifyBean) data;
            if(page==1)
            {
                classAdapter.setlist(classifyBean.getResult());
            }else
            {
                classAdapter.addlist(classifyBean.getResult());
            }
            page++;
            bootom_class_xrecyclerView.refreshComplete();
            bootom_class_xrecyclerView.loadMoreComplete();

        }
    }



}
