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
    public final static String REPAIR_COM = "select_company";
    public final static String TEST = "json";



    public final static int ORDER_STATUS_ALL = 1;//订单列表状态
    public final static int ORDER_STATUS1 = 1;//订单列表状态
    public final static int ORDER_STATUS2 = 2;//订单列表状态
    public final static int ORDER_STATUS3 = 3;//订单列表状态
    public final static int ORDER_STATUS4 = 4;//订单列表状态
    public final static int ORDER_STATUS5 = 5;//订单列表状态


}
