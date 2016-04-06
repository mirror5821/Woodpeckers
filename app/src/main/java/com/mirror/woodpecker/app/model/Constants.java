package com.mirror.woodpecker.app.model;

/**
 * mirror
 */
public interface Constants {
    //配送半径
    public final static double DISTRIBUTION_RADIUS = 1000;//Distribution radius

    //一些用到的常量定义
    public final static int MAP_REQUESTCODE = 1101;
    public final static int ADDRESS_ADD_REQUESTCODE = 1102;
    public final static int ADDRESS_SELECT_REQUESTCODE = 1103;
    public final static int ADDRESS_UPDATE = 1104;

    public final static int LOGIN_CODE1 = 2101;
    public final static int LOGIN_CODE2 = 2102;
    public final static int LOGIN_CODE3 = 2103;
    public final static int LOGIN_CODE4 = 2104;
    public final static int LOGIN_CODE5 = 2105;
    public final static int FORUM_ADD_CODE = 5001;

    public final static int PRODUCT_TYPE1 = 1;//正常商品
    public final static int PRODUCT_TYPE2 = 2;//非标准商品
    public final static String INTENT_ID = "INTENT_ID";
    public final static String TYPE_ID = "TYPE_ID";
    public final static String NOTIFY_TV = "NOTIFY_TV";

    public final static int PAY_TYPE1 = 1;//支付入口1 //购物车进入
    public final static int PAY_TYPE2 = 2;//支付入口2 //未支付订单进入
    public final static int PAY_TYPE3 = 3;//支付入口3 //直接购买进入


//    public final static String BASE_URL = "http://192.168.0.13/index.php?s=/";
    public final static String BASE_URL = "http://zmnyw.cn/index.php?s=/";
//    public final static String BASE_URL = "http://www.lexiangyungou.cn/LXYG/";

//    http://zmnyw.cn/index.php?s=/Home/Api/login
//    http://zmnyw.cn/index.php?s=/Home/Api/register

    //正式上线用这个
    public static final String WECHAT_APP_ID = "wx2d2b54b6349d8ef7";
    public static final String WECHAT_SECRET = "d4624c36b6795d1d99dcf0547af5443d";
    public static final String WECHAT_VALUE = "android_pub";



    //地址
    public final static String LAT = "LAT";
    public final static String LNG = "LNG";
    public final static String ADDRESS = "ADDRESS";
    public final static String CITY = "CITY";
    public final static String COUNTRY = "COUNTRY";
    public final static String DISTRICT = "DISTRICT";
    public final static String PROVINCE = "PROVINCE";
    public final static String NAME = "NAME";
    public final static String PHONE = "PHONE";
    public final static String ADDRESS_ID = "ADDRESS_ID";
    public final static String ADR = "ADR";

    public final static String USER_INFO = "USER_INFO";
    public final static String USER_INFO_PHONE = "USER_INFO_PHONE";
    public final static String USER_INFO_PASS = "USER_INFO_PASS";
    public final static String SHOP_INFO1 = "SHOP_INFO1";

    public final static String SECRET = "caca51";
    public final static String JSONG_KEY = "info";
    public final static String ORDER_DELETE = "delOrder";


    //请求都和地址
    public final static String HOST_HEADER = BASE_URL+"Home/Api/";
    public final static String HOST_IMG_HEADER = BASE_URL+"Home/Api/";

    public final static String ALIPAY_NOTIFY_URL = BASE_URL+"app/pay/alipayNotify";

    public final static String LOGIN_TEST = "login";
    public final static String REGISTERS = "register";
    public final static String UPDATE_PASS = "password";
    public final static String REPAIR_COM = "select_company";
    public final static String REPAIR = "repair";
    public final static String ORDER_LIST_MY = "myorderlist";//我的维修单（myorderlist）参数：用户ID 【 uid】
    public final static String ORDER_LIST_DEPART = "departorderlist";//部门维修单（departorderlist）参数：用户ID 【 uid】
    public final static String ORDER_LIST_COM = "companyorderlist";//单位维修单（companyorderlist）参数：用户ID 【 uid】
    public final static String ORDER_DETAILS = "uorderinfo";//维修单详情
    public final static String KNOW_ZMN = "understand";//了解啄木鸟

    public final static String ORDER_LIST_SERVICE = "orderlist";//客服看到的维修单
    public final static String ORDER_LIST_REPAIR = "repairorderlist";//维修人员维修单列表

    public final static String ORDER_FLOW = "orderhandel";//客服订单流程
    public final static String REPAIR_LIST = "repairlist";//维修人员列表
    public final static String UP_REPARI_PRICE = "price";//提交维修价格
    public final static String COMPLAIN = "complain";//投诉建议
    public final static String COMPLAIN_LIST = "complainlist";//投诉建议列表
    public final static String COMPLAIN_REPLAY_LIST = "replaylist";//投诉回复建议列表
    public final static String COMPLAIN_REPLAY = "complainreplay";//投诉建议回复
    public final static String CONSTANS = "contactus";//联系我们
    public final static String USER_INFOMATION = "uinfo";//用户个人信息
    public final static String ZI_XUN = "consult";//业务咨询
    public final static String ORDER_COMMENT = "uordercomment";//维修单评论
    public final static String XUNJIAN_LIST = "xjremain";//巡检列表
    public final static String XUNJIAN_REPORT = "xjlog";//巡检反馈


    public final static int ORDER_STATUS_ALL = 1;//订单列表状态
    public final static int ORDER_STATUS1 = 1;//订单列表状态
    public final static int ORDER_STATUS2 = 2;//订单列表状态
    public final static int ORDER_STATUS3 = 3;//订单列表状态
    public final static int ORDER_STATUS4 = 4;//订单列表状态
    public final static int ORDER_STATUS5 = 5;//订单列表状态


}
