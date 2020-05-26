package com.young.myaddemo.Model;

import com.young.myaddemo.utils.Constant;
import com.young.myaddemo.utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TaskModel {

    String ads = "{\n" +
            "    \"platform\":28,\n" +
            "    \"api_url\":\"https://cpro.snto.com/app/ad.json\",\n" +
            "    \"adslot_width\":\"640\",\n" +
            "    \"adslot_height\":\"100\",\n" +
            "    \"click_url\":\"http://apis.lubangchem.com/\",\n" +
            "    \"tkcurl\":\"http://apis.lubangchem.com/\",\n" +
            "    \"pos_id\":\"2spisuim523p\",\n" +
            "    \"app_package_name\":\"淘最热点\",\n" +
            "    \"app_ver\":\"12\",\n" +
            "    \"xact_url\":\"http://api.lubangchem.com/xact.php?pt=28\"\n" +
            "}";
    public int platform;
    public InnerAdModel adModel;

    public void parserAds() {
        if (StringUtil.isBlank(ads)) {
            return;
        }
        try {
//            JSONArray jsonArray = new JSONArray(ads);
//            int len = jsonArray.length();
//            if (len > 0) {
//                for (int i = 0; i < len; i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    switch (platform) {
//                        case Constant.SMHD_API:
//                            adModels[i] = new SMHDModel(jsonObject);
//                            break;
//                    }
//                }
//            }
            JSONObject jsonObject = new JSONObject(ads);
            switch (platform) {
                case Constant.ST_API:
                    adModel = new STModel(jsonObject);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
