package com.young.myaddemo.Model;

import com.rbt.vrde.commu.Request;
import com.rbt.vrde.commu.Response;
import com.rbt.vrde.commu.VolleyError;
import com.rbt.vrde.commu.cc.JsonObjectRequest;
import com.rbt.vrde.commu.cc.RequestQueue;
import com.rbt.vrde.commu.cc.Volley;
import com.rbt.vrde.core.CoreManager;
import com.young.myaddemo.utils.Constant;
import com.young.myaddemo.utils.StringUtil;
import com.young.myaddemo.utils.SystemUtil;

import org.json.JSONObject;

import java.util.List;

public class BrushRequestManager {

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
        if (StringUtil.isBlank(requestBody)) {
            return;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, stModel.apiUrl, requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null && callback != null) {
                    List<AdModel> models = Funs.parserArrayAds(innerAdModel, response);
                    callback.onSuccessCallBack(models);
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


}
