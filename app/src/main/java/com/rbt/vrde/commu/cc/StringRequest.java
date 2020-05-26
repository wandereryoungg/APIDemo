/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rbt.vrde.commu.cc;

import com.rbt.vrde.commu.AuthFailureError;
import com.rbt.vrde.commu.NetworkResponse;
import com.rbt.vrde.commu.Request;
import com.rbt.vrde.commu.Response;
import com.rbt.vrde.commu.toolbox.HttpHeaderParser;
import com.rbt.vrde.utils.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * A canned request for retrieving the response body at a given URL as a String.
 */
public class StringRequest extends Request<String> {
    private final Response.Listener<String> mListener;
    private String mRequestBody;
    //private boolean mEncrypt;

    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringRequest(int method, String url, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        this(method, url, null, listener, errorListener);
    }

    public StringRequest(int method, String url, String requestBody, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        mRequestBody = requestBody;
        //mEncrypt = false;
    }

    /*public StringRequest(int method, String url, String reportBody, boolean encrypt, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        mRequestBody = reportBody;
        mEncrypt = encrypt;
    }*/

    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if(StringUtils.isNotBlank(mRequestBody)) {
            try {
                //if(mEncrypt) {
                    //mRequestBody = RC4.encrytRC4(mRequestBody);
                //}
                return mRequestBody.getBytes(getParamsEncoding());
            } catch (UnsupportedEncodingException e) {
//                MyLog.e("StringRequest", "---getBody--Err:" + e.getMessage(), e);
            }
        }
        return null;
    }
}
