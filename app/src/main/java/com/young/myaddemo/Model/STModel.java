package com.young.myaddemo.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class STModel extends InnerAdModel {

    public String activeUrl;


    public STModel(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    void parseJson(JSONObject jsonObject) {
        try {
            platform = jsonObject.getInt("platform");
            apiUrl = jsonObject.getString("api_url");
            activeUrl = jsonObject.getString("xact_url");
            clickUrl = jsonObject.getString("click_url");
            tkcUrl = jsonObject.getString("tkcurl");
            adslotWidth = jsonObject.getInt("adslot_width");
            adslotHeight = jsonObject.getInt("adslot_height");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
