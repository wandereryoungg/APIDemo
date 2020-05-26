package com.young.myaddemo.Model;

import org.json.JSONObject;

public abstract class InnerAdModel {

    public InnerAdModel(JSONObject jsonObject) {
        parseJson(jsonObject);
    }

    abstract void parseJson(JSONObject jsonObject);

    /* 广告平台类型 */
    public int platform;

    /* 第三方广告平台请求地址 */
    public String apiUrl;

    /* 第三方广告平台 App Id */
    public String appId;

    // 点击, Inner service 链接地址
    public String clickUrl;

    // 显示 Inner service 链接地址
    public String tkcUrl;

    // 广告位宽
    public int adslotWidth;

    // 广告位高
    public int adslotHeight;

    // 广告点击权重
    public int clickWeight;
    // 服务器下发时间
    public long showTime;

    // 包名
    public String pkgName;

    // 设备信息
    public DeviceModel deviceInfo;

    public String ipv4;

}
