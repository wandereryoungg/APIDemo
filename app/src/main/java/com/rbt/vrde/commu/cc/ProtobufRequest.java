package com.rbt.vrde.commu.cc;

/**
 * Created by Roy on 2017/7/10.
 */

public class ProtobufRequest /*extends Request<BaiduMobadsApi5.MobadsResponse>*/ {

   /* private final Response.Listener<BaiduMobadsApi5.MobadsResponse> mListener;

    private BaiduMobadsApi5.MobadsRequest mRequestBody = null;

    public ProtobufRequest(String url, BaiduMobadsApi5.MobadsRequest body, Response.Listener<BaiduMobadsApi5.MobadsResponse> listener, Response.ErrorListener errorListener) {
        this(Method.DEPRECATED_GET_OR_POST, url, body, listener, errorListener);
    }

    public ProtobufRequest(int method, String url, BaiduMobadsApi5.MobadsRequest body, Response.Listener<BaiduMobadsApi5.MobadsResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        this.mListener = listener;
        this.mRequestBody = body;
    }

    @Override
    protected Response<BaiduMobadsApi5.MobadsResponse> parseNetworkResponse(NetworkResponse response) {
        BaiduMobadsApi5.MobadsResponse rsp = null;
        try {
            rsp = BaiduMobadsApi5.MobadsResponse.parseFrom(response.data);
        } catch (InvalidProtocolBufferException e) {
//            MyLog.e("ProtobufRequest", e.getMessage(), e);
        }

        return Response.success(rsp, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(BaiduMobadsApi5.MobadsResponse response) {
        mListener.onResponse(response);
    }

    @Override
    public byte[] getPostBody() {
        return getBody();
    }

    @Override
    public byte[] getBody() {
        return mRequestBody == null ? null : mRequestBody.toByteArray();
    }

    @Override
    public String getBodyContentType() {
        return "x-protobuf; charset=" + getParamsEncoding();
    }
    */
}
