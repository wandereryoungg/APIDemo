package com.young.myaddemo.Model;

import java.io.Serializable;

public class AdModel implements Serializable {

    public int platform;    // 广告平台类型

    public String innerClickUrl;   // 广告点击，内部服务器统计地址

    public String innerShowUrl;     //  广告展示，内部服务器统计地址

    public long showTime = -1;       // 广告显示时间戳

    public int width;               // 物料的宽度

    public int height;              // 物料的高度

    public int clickWeight;         // 广告点击权重，控制点击率 1-100

    public int creativeType;        // 创意类型
    public int interactionType;     // 交互类型
    public String clickUrl;         // 点击行为地址
    public String title;            // 推广标题
    public String description;      // 广告描述

}
