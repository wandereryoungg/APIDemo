package com.young.myaddemo.Model;

import com.young.myaddemo.utils.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.young.myaddemo.utils.Constant.ST_API;

public class Funs {

    public static List<AdModel> parserArrayAds(InnerAdModel innerAdModel, Object obj) {
        switch (innerAdModel.platform) {
            case ST_API:
                return parseSTList(innerAdModel, (JSONObject) obj);
        }
        return null;
    }

    public static List<AdModel> parseSTList(InnerAdModel innerAdModel, JSONObject obj) {


        if (obj == null) {
            throw new IllegalArgumentException("parse error with no input");
        }
        List<AdModel> data = new ArrayList<>();
        if (!obj.has("code") || obj.optInt("code") != 200) {
            return data;
        }
        JSONArray jsonArray = obj.optJSONArray("data");
        if (jsonArray == null || jsonArray.length() == 0) {
            return data;
        }
        STApiModel model = new STApiModel();
        model.platform = ST_API;
        //TODO

        JSONObject jsonObject = jsonArray.optJSONObject(0);
        model.adWidth = jsonObject.optInt("ad_width");
        model.adHeight = jsonObject.optInt("ad_height");
        model.landingPage = jsonObject.optString("landing_page");
        model.interactionType = jsonObject.optInt("interaction");
        model.macro = jsonObject.optInt("macro");
        model.imageUrls = jsonArray2Array(jsonObject.optJSONArray("image"));
        model.iconUrls = jsonArray2Array(jsonObject.optJSONArray("icon"));
        model.downloadStartUrls = jsonArray2Array(jsonObject.optJSONArray("download_start_urls"));
        model.downloadEndUrls = jsonArray2Array(jsonObject.optJSONArray("download_end_urls"));
        model.installStartUrls = jsonArray2Array(jsonObject.optJSONArray("install_start_urls"));
        model.installEndUrls = jsonArray2Array(jsonObject.optJSONArray("install_end_urls"));
        model.activeUrls = jsonArray2Array(jsonObject.optJSONArray("active_urls"));
        model.showUrls = jsonArray2Array(jsonObject.optJSONArray("show_urls"));
        model.clickUrls = jsonArray2Array(jsonObject.optJSONArray("click_urls"));

        //model.macrosArrs = jsonArray2Array(jsonObject.optJSONArray("macros_arrs"));
        data.add(model);
        return data;
    }

    private static String[] jsonArray2Array(JSONArray jsonArray) {
        if (jsonArray != null && jsonArray.length() > 0) {
            String[] arrays = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                arrays[i] = jsonArray.optString(i);
            }
            return arrays;
        }
        return null;
    }

}
