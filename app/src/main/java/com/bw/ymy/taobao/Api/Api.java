package com.bw.ymy.taobao.Api;

public class Api {
    //登录
    public   static final  String login="user/v1/login";
    //注册
   public static final String sing="user/v1/register";
   //圈子列表
    public  static  final  String quznzi="circle/v1/findCircleList?userid=1010&sessionid=15320748258726&page=1&count=5";
    //热销
    public  static  final  String sell="commodity/v1/commodityList";
    //XBanner
   public static final String xbanner ="commodity/v1/bannerShow";
    //点击查看更多
   public static final  String more="commodity/v1/findCommodityListByLabel?labelId=%s&page=%d&count=10";
   //点击搜索
    public  static  final  String  search="commodity/v1/findCommodityByKeyword?keyword=%s&page=%d&count=10";
     // 查询一级商品类目
    public static final String URL_FIND_FIRST_CATEGORY_GET="commodity/v1/findFirstCategory";
     //查询二级商品类目
    public static final String URL_FIND_SECOND_CATEGORY_GET="commodity/v1/findSecondCategory?firstCategoryId=%s";
    //根据二级条目查看更多
    public static final String Classsify="commodity/v1/findCommodityByCategory?categoryId=%s&page=1&count=5";


}
