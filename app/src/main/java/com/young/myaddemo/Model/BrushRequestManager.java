package com.young.myaddemo.Model;

import com.rbt.vrde.commu.Request;
import com.rbt.vrde.commu.Response;
import com.rbt.vrde.commu.VolleyError;
import com.rbt.vrde.commu.cc.JsonObjectRequest;
import com.rbt.vrde.commu.cc.RequestQueue;
import com.rbt.vrde.commu.cc.StringRequest;
import com.rbt.vrde.commu.cc.Volley;
import com.rbt.vrde.core.CoreManager;
import com.young.myaddemo.utils.Constant;
import com.young.myaddemo.utils.StringUtil;
import com.young.myaddemo.utils.SystemUtil;
import com.young.myaddemo.utils.YoungLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BrushRequestManager {

    static String responseStr = "{\n" +
            "    \"code\": 200,\n" +
            "    \"msg\": \"success\",\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"title\": \"test title\",\n" +
            "            \"desc\": \"test xxx desc\",\n" +
            "            \"image\": [\n" +
            "                \"http://resource.batzhy.cn/580x90.jpg\"\n" +
            "            ],\n" +
            "            \"icon\": [\n" +
            "                \"http://resource.batzhy.cn/90x90.jpg\"\n" +
            "            ],\n" +
            "            \"interaction\": 1,\n" +
            "            \"macro\": 0,\n" +
            "            \"brand\": \"\",\n" +
            "            \"expiration\": 0,\n" +
            "            \"pos_type\": 1,\n" +
            "            \"ad_width\": 580,\n" +
            "            \"ad_height\": 90,\n" +
            "            \"deep_link\": \"xxxx://\",\n" +
            "            \"landing_page\": \"https://www.baidu.com\",\n" +
            "            \"show_urls\": [\n" +
            "                \"https://www.baidu.com?shownotice=true\",\n" +
            "                \"http://39.97.201.131:8081/ssp/v1/notice/shownotice?c=B239FDF150A4C355B04D0532E816BABB04E4E28A939934E81062344BB837C576DFD62212E188C2FF5FDE042EC88887503ED065AE33737A39924082A5FDCD58981475951539493CCCF92BD874191656805AFF536C56F4F630B357164CD28EFBFF7188EB6BDA4DA1C2874DC0E63A15CF3DDAA6D2E5721A8FD1E31164A9097B4201867A29A7000EF8E73F202375637E4EA96DA045D054EA729A1129743FBD87D584621E761F05126FCCB28F2339596177323080CD8A73A008345B9E5A36323603BB5AC7083BC76B0C09E393E5A5DCD26E531694026291B8D0895AB04CD2F32E0ECA07C00DCDF00BB8F2ABE53EF71B963D4F0F20F05EB2DF9836515B1E3DAE0D28D7D3005CB79A4929D84B7A4F879B3951F62F86EDBB8031ECB00E7FCBE25F1142C8310042F6BA8D2EA534F183386DAF15E07188AB163101CCE8C74F8C9FBDB3A74A66D200381A2216CCF2435DADEBEAD350A18C24435B81393E492ADA92F85C3F5FDBCB0F310BA6FD8AB26182ABCB34A5514366676ACA720C612E7013D8BEB77DAD0E81C1F007968C5DD03DB3320CCC2BDFAB139208F21F38D19AFA3B2360E80BF6E75158585218878C29391DBB70CFC038997D306DAFCA26B488C9F8787D19B80D71C19376A678E80A455414AD584DAD39F7D0E3BE7DCA06F6432867027364437C76A2DEBB3CEF1AAB8802DF4915EC844E60E0811B3745AAE20A905C7EA786AE6DC6A977131B2A637C7B775F70C2DFD8C9B0DECA41F4BF925F592F1DE642C3C0659C8B434B14D54095E72127998991B8B452E88602A49E31B46219CBB9C18F5717\"\n" +
            "            ],\n" +
            "            \"click_urls\": [\n" +
            "                \"https://www.baidu.com?clicknotice=true\",\n" +
            "                \"http://39.97.201.131:8081/ssp/v1/notice/clicknotice?c=B239FDF150A4C355B04D0532E816BABB04E4E28A939934E81062344BB837C576DFD62212E188C2FF5FDE042EC88887503ED065AE33737A39924082A5FDCD58981475951539493CCCF92BD874191656805AFF536C56F4F630B357164CD28EFBFF7188EB6BDA4DA1C2874DC0E63A15CF3DDAA6D2E5721A8FD1E31164A9097B4201867A29A7000EF8E73F202375637E4EA96DA045D054EA729A1129743FBD87D584621E761F05126FCCB28F2339596177323080CD8A73A008345B9E5A36323603BB5AC7083BC76B0C09E393E5A5DCD26E531694026291B8D0895AB04CD2F32E0ECA07C00DCDF00BB8F2ABE53EF71B963D4F0F20F05EB2DF9836515B1E3DAE0D28D7D3005CB79A4929D84B7A4F879B3951F62F86EDBB8031ECB00E7FCBE25F1142C8310042F6BA8D2EA534F183386DAF15E07188AB163101CCE8C74F8C9FBDB3A74A66D200381A2216CCF2435DADEBEAD350A18C24435B81393E492ADA92F85C3F5FDBCB0F310BA6FD8AB26182ABCB34A5514366676ACA720C612E7013D8BEB77DAD0E81C1F007968C5DD03DB3320CCC2BDFAB139208F21F38D19AFA3B2360E80BF6E75158585218878C29391DBB70CFC038997D306DAFCA26B488C9F8787D19B80D71C19376A678E80A455414AD584DAD39F7D0E3BE7DCA06F6432867027364437C76A2DEBB3CEF1AAB8802DF4915EC844E60E0811B3745AAE20A905C7EA786AE6DA8CC66E42427C8859DC3CA66A50A7A2B4401A3A16F0DD95A269EF88EFCFC96756FFE8D1DC74EE2D1F8D274385B823C318024C27E7B42E9B9F7A6192227C26F25\"\n" +
            "            ],\n" +
            "            \"pkg_name\": \"\",\n" +
            "            \"app_name\": \"\",\n" +
            "            \"app_size\": 0,\n" +
            "            \"download_start_urls\": [\n" +
            "                \"https://www.baidu.com?downstart=true\",\n" +
            "                \"http://39.97.201.131:8081/ssp/v1/notice/downloadstart?c=B239FDF150A4C355B04D0532E816BABB04E4E28A939934E81062344BB837C576DFD62212E188C2FF5FDE042EC88887503ED065AE33737A39924082A5FDCD58981475951539493CCCF92BD874191656805AFF536C56F4F630B357164CD28EFBFF7188EB6BDA4DA1C2874DC0E63A15CF3DDAA6D2E5721A8FD1E31164A9097B4201867A29A7000EF8E73F202375637E4EA96DA045D054EA729A1129743FBD87D584621E761F05126FCCB28F2339596177323080CD8A73A008345B9E5A36323603BB5AC7083BC76B0C09E393E5A5DCD26E531694026291B8D0895AB04CD2F32E0ECA07C00DCDF00BB8F2ABE53EF71B963D4F0F20F05EB2DF9836515B1E3DAE0D28D7D3005CB79A4929D84B7A4F879B3951F62F86EDBB8031ECB00E7FCBE25F1142C8310042F6BA8D2EA534F183386DAF15E07188AB163101CCE8C74F8C9FBDB3A74A66D200381A2216CCF2435DADEBEAD350A18C24435B81393E492ADA92F85C3F5FDBCB0F310BA6FD8AB26182ABCB34A5514366676ACA720C612E7013D8BEB77DAD0E81C1F007968C5DD03DB3320CCC2BDFAB139208F21F38D19AFA3B2360E80BF6E75158585218878C29391DBB70CFC038997D306DAFCA26B488C9F8787D19B80D71C19376A678E80A455414AD584DAD39F7D0E3BE7DCA06F6432867027364437C76A2DEBB3CEF1AAB8802DF4915EC844E60E0811B3745AAE20A905C7EA786AE6D3C45A8C7AC42E498D4E572777FB80C9573DE7F2C502588CDD7CE404AFE3108EEE88934831B0902C1FBC7ACA9AAF965EF06B730B62BCF5C984E4A68D576BBC38C\"\n" +
            "            ],\n" +
            "            \"download_end_urls\": [\n" +
            "                \"https://www.baidu.com?downend=true\",\n" +
            "                \"http://39.97.201.131:8081/ssp/v1/notice/downloadend?c=B239FDF150A4C355B04D0532E816BABB04E4E28A939934E81062344BB837C576DFD62212E188C2FF5FDE042EC88887503ED065AE33737A39924082A5FDCD58981475951539493CCCF92BD874191656805AFF536C56F4F630B357164CD28EFBFF7188EB6BDA4DA1C2874DC0E63A15CF3DDAA6D2E5721A8FD1E31164A9097B4201867A29A7000EF8E73F202375637E4EA96DA045D054EA729A1129743FBD87D584621E761F05126FCCB28F2339596177323080CD8A73A008345B9E5A36323603BB5AC7083BC76B0C09E393E5A5DCD26E531694026291B8D0895AB04CD2F32E0ECA07C00DCDF00BB8F2ABE53EF71B963D4F0F20F05EB2DF9836515B1E3DAE0D28D7D3005CB79A4929D84B7A4F879B3951F62F86EDBB8031ECB00E7FCBE25F1142C8310042F6BA8D2EA534F183386DAF15E07188AB163101CCE8C74F8C9FBDB3A74A66D200381A2216CCF2435DADEBEAD350A18C24435B81393E492ADA92F85C3F5FDBCB0F310BA6FD8AB26182ABCB34A5514366676ACA720C612E7013D8BEB77DAD0E81C1F007968C5DD03DB3320CCC2BDFAB139208F21F38D19AFA3B2360E80BF6E75158585218878C29391DBB70CFC038997D306DAFCA26B488C9F8787D19B80D71C19376A678E80A455414AD584DAD39F7D0E3BE7DCA06F6432867027364437C76A2DEBB3CEF1AAB8802DF4915EC844E60E0811B3745AAE20A905C7EA786AE6D3C45A8C7AC42E498D4E572777FB80C9594550EAA3159F7EFA925680E5FBD80246FFE8D1DC74EE2D1F8D274385B823C318024C27E7B42E9B9F7A6192227C26F25\"\n" +
            "            ],\n" +
            "            \"install_start_urls\": [\n" +
            "                \"https://www.baidu.com?inststart=true\",\n" +
            "                \"http://39.97.201.131:8081/ssp/v1/notice/installstart?c=B239FDF150A4C355B04D0532E816BABB04E4E28A939934E81062344BB837C576DFD62212E188C2FF5FDE042EC88887503ED065AE33737A39924082A5FDCD58981475951539493CCCF92BD874191656805AFF536C56F4F630B357164CD28EFBFF7188EB6BDA4DA1C2874DC0E63A15CF3DDAA6D2E5721A8FD1E31164A9097B4201867A29A7000EF8E73F202375637E4EA96DA045D054EA729A1129743FBD87D584621E761F05126FCCB28F2339596177323080CD8A73A008345B9E5A36323603BB5AC7083BC76B0C09E393E5A5DCD26E531694026291B8D0895AB04CD2F32E0ECA07C00DCDF00BB8F2ABE53EF71B963D4F0F20F05EB2DF9836515B1E3DAE0D28D7D3005CB79A4929D84B7A4F879B3951F62F86EDBB8031ECB00E7FCBE25F1142C8310042F6BA8D2EA534F183386DAF15E07188AB163101CCE8C74F8C9FBDB3A74A66D200381A2216CCF2435DADEBEAD350A18C24435B81393E492ADA92F85C3F5FDBCB0F310BA6FD8AB26182ABCB34A5514366676ACA720C612E7013D8BEB77DAD0E81C1F007968C5DD03DB3320CCC2BDFAB139208F21F38D19AFA3B2360E80BF6E75158585218878C29391DBB70CFC038997D306DAFCA26B488C9F8787D19B80D71C19376A678E80A455414AD584DAD39F7D0E3BE7DCA06F6432867027364437C76A2DEBB3CEF1AAB8802DF4915EC844E60E0811B3745AAE20A905C7EA786AE6D484BF3D8AF455A61802C2B03DE86AB7509767F54E37EF5C8765F0BCFF0615A74B43ABB83565D6FD9C5F45DCE23CDFCA34779D9BAAA36BEF4148FD37FF9033251\"\n" +
            "            ],\n" +
            "            \"install_end_urls\": [\n" +
            "                \"https://www.baidu.com?downend=true\",\n" +
            "                \"http://39.97.201.131:8081/ssp/v1/notice/installend?c=B239FDF150A4C355B04D0532E816BABB04E4E28A939934E81062344BB837C576DFD62212E188C2FF5FDE042EC88887503ED065AE33737A39924082A5FDCD58981475951539493CCCF92BD874191656805AFF536C56F4F630B357164CD28EFBFF7188EB6BDA4DA1C2874DC0E63A15CF3DDAA6D2E5721A8FD1E31164A9097B4201867A29A7000EF8E73F202375637E4EA96DA045D054EA729A1129743FBD87D584621E761F05126FCCB28F2339596177323080CD8A73A008345B9E5A36323603BB5AC7083BC76B0C09E393E5A5DCD26E531694026291B8D0895AB04CD2F32E0ECA07C00DCDF00BB8F2ABE53EF71B963D4F0F20F05EB2DF9836515B1E3DAE0D28D7D3005CB79A4929D84B7A4F879B3951F62F86EDBB8031ECB00E7FCBE25F1142C8310042F6BA8D2EA534F183386DAF15E07188AB163101CCE8C74F8C9FBDB3A74A66D200381A2216CCF2435DADEBEAD350A18C24435B81393E492ADA92F85C3F5FDBCB0F310BA6FD8AB26182ABCB34A5514366676ACA720C612E7013D8BEB77DAD0E81C1F007968C5DD03DB3320CCC2BDFAB139208F21F38D19AFA3B2360E80BF6E75158585218878C29391DBB70CFC038997D306DAFCA26B488C9F8787D19B80D71C19376A678E80A455414AD584DAD39F7D0E3BE7DCA06F6432867027364437C76A2DEBB3CEF1AAB8802DF4915EC844E60E0811B3745AAE20A905C7EA786AE6D484BF3D8AF455A61802C2B03DE86AB75382B08EAF0F634357078A157C26B665D9C8B434B14D54095E72127998991B8B452E88602A49E31B46219CBB9C18F5717\"\n" +
            "            ],\n" +
            "            \"active_urls\": [\n" +
            "                \"https://www.baidu.com?active=true\",\n" +
            "                \"http://39.97.201.131:8081/ssp/v1/notice/active?c=B239FDF150A4C355B04D0532E816BABB04E4E28A939934E81062344BB837C576DFD62212E188C2FF5FDE042EC88887503ED065AE33737A39924082A5FDCD58981475951539493CCCF92BD874191656805AFF536C56F4F630B357164CD28EFBFF7188EB6BDA4DA1C2874DC0E63A15CF3DDAA6D2E5721A8FD1E31164A9097B4201867A29A7000EF8E73F202375637E4EA96DA045D054EA729A1129743FBD87D584621E761F05126FCCB28F2339596177323080CD8A73A008345B9E5A36323603BB5AC7083BC76B0C09E393E5A5DCD26E531694026291B8D0895AB04CD2F32E0ECA07C00DCDF00BB8F2ABE53EF71B963D4F0F20F05EB2DF9836515B1E3DAE0D28D7D3005CB79A4929D84B7A4F879B3951F62F86EDBB8031ECB00E7FCBE25F1142C8310042F6BA8D2EA534F183386DAF15E07188AB163101CCE8C74F8C9FBDB3A74A66D200381A2216CCF2435DADEBEAD350A18C24435B81393E492ADA92F85C3F5FDBCB0F310BA6FD8AB26182ABCB34A5514366676ACA720C612E7013D8BEB77DAD0E81C1F007968C5DD03DB3320CCC2BDFAB139208F21F38D19AFA3B2360E80BF6E75158585218878C29391DBB70CFC038997D306DAFCA26B488C9F8787D19B80D71C19376A678E80A455414AD584DAD39F7D0E3BE7DCA06F6432867027364437C76A2DEBB3CEF1AAB8802DF4915EC844E60E0811B3745AAE20A905C7EA786AE6DA53738C1F81A3A7C9E9C689ECAF047744521D65CB8D741EDC4C67FA70508DA4D6C23DA7B58FEF3260DE545916ADB767D5D19A5920ACA64AC4474FE4C12000A4C\"\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    public interface RequestCallback<T> {
        void onSuccessCallBack(T response, Object... objs);

        void onFailCallback(Exception e);
    }

    private static RequestQueue mRequestQueue;

    static {
        mRequestQueue = Volley.newRequestQueue(CoreManager.getContext());
    }

    public static void postAdRequest(InnerAdModel innerAdModel, RequestCallback<List<AdModel>> callback) {
        switch (innerAdModel.platform) {
            case Constant.ST_API:
                postSTApiRequest(innerAdModel, callback);
                break;
        }
    }

    private static void postSTApiRequest(final InnerAdModel innerAdModel, final RequestCallback callback) {
        STModel stModel = (STModel) innerAdModel;
        String requestBody = BrushRequestParams.generateSTApiParams(stModel);
        YoungLog.logE("requestBody= " + requestBody);
        if (StringUtil.isBlank(requestBody)) {
            return;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://119.27.167.20/app/ad.json", requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null && callback != null) {
                    YoungLog.logE("返回数据: " + response.toString());
                    try {
                        JSONObject object = new JSONObject(responseStr);
                        YoungLog.logE("模拟数据: " + object.toString());
                        List<AdModel> models = Funs.parserArrayAds(innerAdModel, object);
                        callback.onSuccessCallBack(models);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (callback != null) {
                    callback.onFailCallback(error);
                }
            }
        });
        jsonObjectRequest.addHeader("Content-Type", " application/json");
        jsonObjectRequest.addHeader("User-Agent", SystemUtil.getUserAgent());

        mRequestQueue.add(jsonObjectRequest);
    }

    public static void postExposure(String url, String userAgent, final RequestCallback<String> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccessCallBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailCallback(error);
            }
        });
        if (StringUtil.isNotBlank(userAgent)) {
            stringRequest.addHeader(com.rbt.vrde.core.res.R.ads.net.BrushRequestManager._User_Agent, userAgent);
        }
        mRequestQueue.add(stringRequest);
    }


}
