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
    String devInfo = "{\n" +
            "\"osn\": \"9.0\",\n" +
            "\"vendor\": \"HUAWEI\",\n" +
            "\"model\": \"HMA-AL00\",\n" +
            "\"screen_sz\": \"1080*2163\",\n" +
            "\"imei\": \"864492045125857\",\n" +
            "\"android_id\": \"f4e511a9ca5520e6\",\n" +
            "\"mac\": \"d0:16:b4:63:d8:4f\",\n" +
            "\"imsi\": \"460021848686135\",\n" +
            "\"cpu\": \"\",\n" +
            "\"ua\": \"Mozilla\\/5.0 (Linux; Android 9; HMA-AL00 Build\\/HUAWEIHMA-AL00; wv) AppleWebKit\\/537.36 (KHTML, like Gecko) Version\\/4.0 Chrome\\/70.0.3538.110 Mobile Safari\\/537.36\",\n" +
            "\"enduid\": \"0ba2044b403b49bf01b34bee59e7c847\",\n" +
            "\"osv\": 28\n" +
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
            JSONObject devJsonObject = new JSONObject(devInfo);
            switch (platform) {
                case Constant.ST_API:
                    adModel = new STModel(jsonObject);
                    adModel.deviceInfo = new DeviceModel(devJsonObject);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
