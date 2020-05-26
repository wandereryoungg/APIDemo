package com.rbt.vrde.utils;

/**
 * Created by Roy on 2017/7/6.
 * <p>
 * 常量类
 */

public interface Constant {

    /**
     * 开发者模式
     */
    boolean isDebugger = false;

    /**
     * SharedPreferences file name
     */
    public String PREFS_FILE_NAME = com.rbt.vrde.utils.R.utils.Constant.__YiAdvData;
    public String PREFS_ERROR_FILE_NAME = com.rbt.vrde.utils.R.utils.Constant.__YiErrorMsg;

    /**
     * SDK 版本号
     */
    public String VERSION_CODE = com.rbt.vrde.utils.R.utils.Constant._SDK_VERSION_;
    //public String VERSION_CODE = "1.4.2";

    /**
     * 锐一广告平台URL
     */
//    public String INNER_URL = "http://test4.haitwo.com/api_two.php";        //测试环境
    public String INNER_URL = com.rbt.vrde.utils.R.utils.Constant._INNER_SERVER_URL;        //线上环境

    //public String DOMAIN_URL = "http://test4.haitwo.com/domain.php";
    public String DOMAIN_URL = com.rbt.vrde.utils.R.utils.Constant._DOMAIN_URL_;
    //
    public String INNER_URL_ADDRESS = com.rbt.vrde.utils.R.utils.Constant._INNER_URL_ADDRESS_;

    /**
     * 获取IP地址
     */
    public String INNER_IP_URL = com.rbt.vrde.utils.R.utils.Constant._INNER_IP_URL;

    /**
     * 淘宝令URL
     */
//    public String TB_CLIPBOARD_URL = "http://192.168.199.198/app.com/app_watch.php";
//    public String TB_CLIPBOARD_URL = "http://test4.haitwo.com/app_watch.php";
    public String TB_CLIPBOARD_URL = com.rbt.vrde.utils.R.utils.Constant._TB_CLIPBOARD_URL;

    /**
     * 异常日志上传
     */
    public String UPLOAD_FILE_URL = com.rbt.vrde.utils.R.utils.Constant.__8935;  // 线上环境端口

    /**
     * 椰子广告平台
     */
    public int AYANG_ADV = 1;

    /**
     * 尚亿广告平台
     */
    public int SHANGYI_ADV = 2;
    /**
     * 悠扬广告平台
     */
    public int YOUYANG_ADV = 3;
    /**
     * 鸿途信达广告平台
     */
    public int HTXD_ADV = 4;

    /**
     * CX 视频贴片广告
     */
    public int CX_MOVIE_ADV = 5;

    /**
     * KMob 广告
     */
    public int KMOB_ADV = 6;

    /**
     * 百度广告
     */
    public int BAIDU_ADV = 7;

    /**
     * ZMeng
     */
    public int ZMENG_ADV = 8;

    /**
     * Imageter
     */
    public int IMAGETER_ADV = 9;

    /**
     * FMOBI
     */
    public int FMOBI_ADV = 10;

    /**
     * CPD APP
     */
    public int CPD_APP = 11;

    /**
     * BaiTong
     */
    public int BAITONG_APP = 12;

    /**
     * TouTiao
     */
    public int JRTT_ADV = 13;

    /**
     * TouTiao2
     */
    public int JRTT2_ADV = 14;

    /**
     * 360
     */
    public int APIQIHU_ADV = 15;

    /**
     * 58
     */
    public int API58_ADV = 16;

    /**
     * 头条信息流
     */
    public int TOUTIAO_INFO = 17;

    /**
     * 58 v2
     */
    public int API58V2_ADV = 18;
    /**
     * 西瓜 CPD
     */
    public int XIGUA_CPD = 19;

    /**
     * XiaoMi
     */
    public int XiaoMi_ADV = 20;

    /**
     * 网聚
     */
    public int WANGJU_ADV = 21;

    /**
     * UC api
     */
    public int UC_ADV = 22;

    /**
     * 豌豆荚 CPD
     */
    public int WDJ_CPD = 23;

    /**
     * 联想 ADX
     */
    public int LX_ADX = 24;

    /**
     * 思瑞盛创ucApi
     */
    public int SRSC_ADV = 25;
    /**
     * milatu cpd
     */
    public int MILATU_CPD = 26;

    /**
     * 思盟互动api
     */
    public int SMHD_API = 27;

    /**
     * 盛通UcApi
     */
    public int ST_API = 28;

    /**
     * Harmight 广告交易平台
     */
    public int Harmight = 29;
    /**
     * 广告效果跟踪信息
     */
    // 广告显示
    public int AD_EXPOSURE = 1;
    // 广告App下载
    public int APP_AD_DOWNLOAD = 102000;
    // 广告App安装
    public int APP_AD_INSTALL = 102001;
    // 广告App激活
    public int APP_AD_ACTIVE = 102002;
    //开始下载app
    public int APP_AD_START_DOWNLOAD = 102009;
    //开始下载app
    public int APP_AD_START_INSTALL = 102010;

    /**
     * 广告请求
     */
    int AD_TRACKING_REQUEST = 103000;
    /**
     * 广告任务开始
     */
    int AD_TRACKING_IMPRESSION = 103001;
    /**
     * 广告任务完成
     */
    int AD_TRACKING_COMPLETE = 103002;

    String EX_NAME = com.rbt.vrde.utils.R.utils.Constant._ex_name;

    /**
     * PopupAd Type : 1=开屏广告、0=插屏广告
     */
    int INTERSTITIAL_AD = 0;
    int SPLASH_AD = 1;

    /**
     * 广告任务名称
     */
//    String ADV_TASK_NAME = "adv";
    String ADV_TASK_NAME = com.rbt.vrde.utils.R.utils.Constant._brush_adv;


    /**
     * API-VERSION
     */
    String API_VERSION_KEY = com.rbt.vrde.utils.R.utils.Constant._Api_Version;
    String API_VERSION_CODE = com.rbt.vrde.utils.R.utils.Constant._4_;

    String CONTENT_DEVICE_KEY = com.rbt.vrde.utils.R.utils.Constant._Content_Device;

}
