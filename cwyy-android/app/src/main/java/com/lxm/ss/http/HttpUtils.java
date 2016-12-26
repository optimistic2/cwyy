package com.lxm.ss.http;

import android.content.Context;
import android.webkit.CookieManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.lxm.ss.CwyyApplication;
import com.lxm.ss.util.Zlog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lxm on 2016/11/9.
 */

public class HttpUtils {

    private static HttpUtils mHttpUtils;

    public synchronized static HttpUtils getInstance() {

//        synchronized(FFApplication.getInstance()) {
        if (mHttpUtils == null) {
            mHttpUtils = new HttpUtils();
        }
//        }
        return mHttpUtils;
    }

    /**
     * @param url
     * @param
     */
    public synchronized void executeJsonObjectHttpPost(Context mContext,
                                                       String url, Map<String, String> params, VolleyListenerInterface volleyListenerInterface) {
        Zlog.ii("httppost :" + url + "  ");
        Zlog.ii("httppost :" + params + "  ");
        RequestQueue volleyQueue = CwyyApplication.getRequestQueue();
        // 创建当前的请求，获取字符串内容
//        stringRequest = new StringRequest(Request.Method.POST, url, volleyListenerInterface.responseListener(), volleyListenerInterface.errorListener());
        if (params == null){
            params = new HashMap<>();
        }
        JSONObject jsonObject = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, volleyListenerInterface.responseJosnObjectListener(), volleyListenerInterface.errorListener()) {
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        // 为当前请求添加标记
        jsonObjectRequest.setTag(mContext);
        Zlog.ii("lxm ss executeJsonObjectHttpPost " + volleyQueue);
        // 将当前请求添加到请求队列中
        volleyQueue.add(jsonObjectRequest);
        // 重启当前请求队列
//        volleyQueue.start();
    }

    public synchronized void executeStringHttpGet(Context mContext, String url, VolleyListenerInterface volleyListenerInterface) {
        RequestQueue volleyQueue = CwyyApplication.getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, volleyListenerInterface.responseStringListener(), volleyListenerInterface.errorListener());
        stringRequest.setTag(mContext);
        volleyQueue.add(stringRequest);
        // 重启当前请求队列
//        volleyQueue.start();
    }

    public synchronized void executeHttpCookie(Context mContext, String url, VolleyListenerInterface volleyListenerInterface) {
        RequestQueue volleyQueue = CwyyApplication.getRequestQueue();

        CookieRequest objRequest = new CookieRequest(url, null, volleyListenerInterface.responseJosnObjectListener(), volleyListenerInterface.errorListener());
        objRequest.setCookie(CookieManager.getInstance().getCookie(NetUtils.APP_MAIN_URL));
//        objRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        objRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        objRequest.setTag(mContext);  // 用于取消请求的tag;
        volleyQueue.add(objRequest);
    }
    /**
     *
     * @param mContext
     * @param volleyListenerInterface
     */
//    public synchronized void executeHttpString(Context mContext, VolleyListenerInterface volleyListenerInterface) {
//        RequestQueue volleyQueue = CwyyApplication.getRequestQueue();
//        StringRequest stringRequest = new StringRequest(NetUtils.APP_CDN_CHECK,
//                volleyListenerInterface.responseStringListener(),
//                volleyListenerInterface.errorListener()
//        );
//        stringRequest.setTag(mContext);  // 用于取消请求的tag;
//        volleyQueue.add(stringRequest);
//    }

//    public synchronized void executeCheck(Context mContext, String url) {
//
//        RequestQueue volleyQueue = CwyyApplication.getRequestQueue();
//        StringRequest stringRequest = new StringRequest(url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String s) {
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                    }
//                }
//        );
//        stringRequest.setTag(mContext);  // 用于取消请求的tag;
//        volleyQueue.add(stringRequest);
//        // 重启当前请求队列
////        volleyQueue.start();
//    }

//    /**
//     *cdn测试（特殊）
//     * @param mContext
//     */
//    public synchronized <T> void executeCdnCheck(final Context mContext) {

//        RequestQueue volleyQueue = CwyyApplication.getRequestQueue();
//        StringRequest stringRequest = new StringRequest(NetUtils.APP_CDN_CHECK,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String s) {
//                        try {
//                            JSONArray jsonArray = new JSONArray(s);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//
//                                //请求开始的时间
//                                Calendar begin = Calendar.getInstance();
//                                SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//                                dfs.setCalendar(begin);
//                                VolleyListenerInterface<T> volleyListenerInterface = new VolleyListenerInterface<T>(mContext, RequestTypeConstant.REQUEST_TYPE_CDN_TEST, RequestTypeConstant.RETURN_JSON_MESSAGE, null,
//                                        null,jsonArray.get(i).toString(),begin);
//
//                                executeHttpString(mContext,volleyListenerInterface);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//
//                    }
//                }
//        );
//        stringRequest.setTag(mContext);  // 用于取消请求的tag;
//
//        volleyQueue.add(stringRequest);
        //// 重启当前请求队列
//        volleyQueue.start();
//    }

    /**
     * // 清除请求队列中的tag标记请求
     *
     * @param tag
     */
    public void cancelRequest(Context tag) {
        CwyyApplication.getRequestQueue().cancelAll(tag);
    }

}
