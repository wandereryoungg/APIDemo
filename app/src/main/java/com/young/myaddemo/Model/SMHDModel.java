package com.young.myaddemo.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class SMHDModel extends InnerAdModel{

    public String userAgent;
    public String activeUrl;
    public String requestBody;


    public SMHDModel(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    void parseJson(JSONObject jsonObject) {
        if(jsonObject == null){
            return;
        }
        try {
            platform = jsonObject.getInt("platform");
            apiUrl = jsonObject.getString("api_url");
            activeUrl = jsonObject.getString("xact_url");
            clickUrl = jsonObject.getString("click_url");
            tkcUrl = jsonObject.getString("tkcurl");
            //appId = jsonObject.getString("channelId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
