package com.lxm.ss.http;

import android.content.Context;
import android.os.Handler;
import android.webkit.CookieManager;

import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lxm on 2016/11/9.
 */

public class ClubHttpPost {

    private static ClubHttpPost httpPost;

    public static ClubHttpPost getInstance() {

        if (httpPost == null) {
            httpPost = new ClubHttpPost();
        }
        return httpPost;
    }

    /*
     * 取消请求
     */
    public void clearTags(Context mContext) {
        HttpUtils.getInstance().cancelRequest(mContext);
    }

//    /**
//     * 网络请求（返回的数据直接取message的值）
//     *
//     * @param mRequestType
//     * @see RequestTypeConstant
//     * @param url
//     * @see NetUtil
//     * @param data 传入参数
//     * @param mContext
//     * @param mHandler
//     */
//    private void doPostHttpReturnMessage(int mRequestType, String url, String data, Context mContext,
//                                         Handler mHandler) {
//        doPostHttp(mRequestType, url, data, RequestTypeConstant.RETURN_JSON_MESSAGE, mContext,
//                mHandler, null);
//    }
//
//    /**
//     * 网络请求（返回的数据需二次解析）
//     *
//     * @param mRequestType
//     * @see RequestTypeConstant
//     * @param url
//     * @see NetUtils
//     * @param data 传入参数
//     * @param mContext
//     * @param mHandler
//     * @param typeReference 需解析的数据的类型，例如： 1.需解析list，new TypeReference<List<Makers>>() {} 2.需解析对象，new
//     *        TypeReference<Makers>() {} 3.returnType为RETURN_JSON_MESSAGE时，该参数为null
//     */
//    private <T> void doPostHttpReturnJson(int mRequestType, String url, String data,
//                                          Context mContext, Handler mHandler, TypeReference<T> typeReference) {
//        doPostHttp(mRequestType, url, data, RequestTypeConstant.RETURN_INITJSON_DATA, mContext,
//                mHandler, typeReference);
//    }

    /**
     * 网络请求httppost
     *
     * @param mRequestType
     * @param url
     * @param params       传入参数
     * @param returnType   是否需要解析 @see RequestTypeConstant
     * @param mContext
     * @param mHandler
     * @param params       需解析的数据的类型，例如： 1.需解析list，new TypeReference<List<Makers>>() {} 2.需解析对象，new
     *                     TypeReference<Makers>() {} 3.returnType为RETURN_JSON_MESSAGE时，该参数为null
     * @see RequestTypeConstant
     * @see NetUtils
     */
    private synchronized <T> void doPostHttpPost(int mRequestType, String url, Map<String, String> params,
                                                 int returnType, Context mContext, Handler mHandler, TypeToken<T> typeToken) {// TODO

        //请求开始的时间
        Calendar begin = Calendar.getInstance();
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        dfs.setCalendar(begin);

        VolleyListenerInterface<T> volleyListenerInterface = new VolleyListenerInterface<T>(mContext, mRequestType, returnType, mHandler,
                typeToken, url, begin);
        HttpUtils.getInstance().executeJsonObjectHttpPost(mContext, url, params, volleyListenerInterface);
    }

    /**
     * httpcookie
     *
     * @param mRequestType
     * @param url
     * @param returnType
     * @param mContext
     * @param mHandler
     * @param typeToken
     * @param <T>
     */
    private synchronized <T> void doPostHttpCookie(int mRequestType, String url,
                                                   int returnType, Context mContext, Handler mHandler, TypeToken<T> typeToken) {// TODO
        //请求开始的时间
        Calendar begin = Calendar.getInstance();
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        dfs.setCalendar(begin);
        VolleyListenerInterface<T> volleyListenerInterface = new VolleyListenerInterface<T>(mContext, mRequestType, returnType, mHandler,
                typeToken, url, begin);
        HttpUtils.getInstance().executeHttpCookie(mContext, url, volleyListenerInterface);
    }

    /**
     * //根据ip获取国家code和name
     * {"areaCode":"0","city":"Hangzhou","country":"China","countryCode":"CN","latitude":"30.293594","longitude":"120.16141","region":"02"}
     */
    public void getCounryCode(Context mContext, Handler mHandler) {

//        Zlog.ii("lxm httpost:getCounryCode");
//        TypeToken<CountryCode> typeToken = new TypeToken<CountryCode>() {
//        };
//        doPostHttpCookie(RequestTypeConstant.REQUEST_TYPE_GET_COUNTRYCODE, NetUtils.CLUB_GET_COUNTRYDATA, RequestTypeConstant.RETURN_INITJSON_DATA, mContext, mHandler, typeToken);

    }



}
