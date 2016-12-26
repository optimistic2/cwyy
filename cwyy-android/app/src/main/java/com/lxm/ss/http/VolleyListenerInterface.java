package com.lxm.ss.http;

import com.lxm.ss.util.Zlog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lxm.ss.pojo.HttpJsonResult;
import com.lxm .ss.util.GsonUtils;

import org.json.JSONObject;

import java.util.Calendar;
/**
 * Created by lxm ss on 2016/11/9.
 */

public class VolleyListenerInterface<T> {

    public Context mContext;
//    public static Response.Listener<JSONObject> mJsonObjectListener;
//    public static Response.Listener<String> mStringListener;
//    public static Response.ErrorListener mErrorListener;

    private int mRequestType;// 请求类型
    private int returnType; // 是否需要进一步解析
    private Handler mHandler;
    private TypeToken<T> typeToken;// 返回数据类型
    private String url;// 请求地址
    private String failedPrompt = "Server connection failure";

    private Calendar begin ;

    public VolleyListenerInterface(Context mContext, int mRequestType, int returnType, Handler mHandler,
                                   TypeToken<T> typeToken, String url, Calendar begin) {
        VolleyListenerInterface.this.mContext = mContext;
        VolleyListenerInterface.this.mRequestType = mRequestType;
        VolleyListenerInterface.this.returnType = returnType;
        VolleyListenerInterface.this.mHandler = mHandler;
        VolleyListenerInterface.this.typeToken = typeToken;
        VolleyListenerInterface.this.url = url;
        VolleyListenerInterface.this.begin = begin;
    }

    public VolleyListenerInterface() {
        super();
    }


    // 请求成功时的回调函数
//    public abstract void onJsonObjectSuccess(JSONObject result);
//    public abstract void onStringSuccess(String result);

    // 请求失败时的回调函数
//    public abstract void onMyError(VolleyError error);

    // 创建JonsObject请求的事件监听
    public Response.Listener<JSONObject> responseJosnObjectListener() {
        Response.Listener<JSONObject> mJsonObjectListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject s) {

//                doPostCheck(url,true);
                if (s != null) {
                    String json = s.toString();
                    Zlog.ii("lxm ss volley onResponse:"+ mRequestType + "  "+  s.toString());
                    initJson(json);
                }else {
                    Zlog.ii("lxm ss volley onResponse:null" + mRequestType);
                    if (mHandler != null) {
                        mHandler.obtainMessage(mRequestType, NetUtils.SERVER_RETURN_NULL, 0, failedPrompt)
                                .sendToTarget();
                    }
                }
            }
        };
        return mJsonObjectListener;
    }
    // 创建String请求的事件监听
    public Response.Listener<String> responseStringListener() {
        Response.Listener<String> mStringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
//                doPostCheck(url,true);
                switch (mRequestType) {


                    default:
                        break;
                }

            }
        };
        return mStringListener;
    }

    // 创建请求失败的事件监听
    public Response.ErrorListener errorListener() {
        Response.ErrorListener mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                onMyError(volleyError);
//                saveLocalData(false);
//                doPostCheck(url,false);
                Zlog.ii("lxm ss volley onErrorResponse:"+ mRequestType+  volleyError.getMessage());
                if (mHandler != null) {
                    mHandler.obtainMessage(mRequestType, NetUtils.SERVER_RETURN_ERROR, 0, failedPrompt)
                            .sendToTarget();
                }
                //处理保存数据
                switch(mRequestType) {


                    default:
                        break;
                }

            }
        };
        return mErrorListener;
    }

    /**
     * 回执 请求时间
     * @param isSucceed
     */
//    private synchronized void doPostCheck(String checkUrl , boolean isSucceed) {
//        java.text.DecimalFormat df = new java.text.DecimalFormat("#.000");
//        double between = (double) ((Calendar.getInstance()).getTimeInMillis() - begin.getTimeInMillis()) / 1000;
//        String url = "" ;
//        if (isSucceed) {
//
//        }else {
//
//        }
//        HttpUtils.getInstance().executeCheck(mContext,url);
//    }

    /**
     * 解析
     *
     */
    private synchronized void initJson(String response) {
        Zlog.ii("lxm ss httppost initJson 1:" + response);


        if (TextUtils.isEmpty(response)) {
            Zlog.ii("lxm ss httppost initJson 4:" + response);
            if (mHandler != null) {
                mHandler.obtainMessage(mRequestType, NetUtils.SERVER_RETURN_NULL, 0, failedPrompt)
                        .sendToTarget();
            }
        } else {
            Zlog.ii("lxm ss httppost  initJson 2:" + response);

            HttpJsonResult httpJsonResult = parseJsonData(response);

            if (httpJsonResult == null) {
                if (mHandler != null) {
                    mHandler.obtainMessage(mRequestType, NetUtils.SERVER_RETURN_ERROR, 0, failedPrompt)
                            .sendToTarget();
                }
            }
            Object obj = null;
            switch (returnType) {
                case RequestTypeConstant.RETURN_INITJSON_DATA: {
                    Zlog.ii("lxm ss httppost  initJson 3:" + response);

                    obj = parseJsonMessage(response);
                    if (obj != null) {
                        Zlog.ii("lxm ss httppost  initJson 4:" + obj);
                        if (mHandler != null) {
                            mHandler.obtainMessage(mRequestType, NetUtils.SERVER_RETURN_OK, 0, obj).sendToTarget();
                        }
                    } else {
                        if (mHandler != null) {
                                mHandler.obtainMessage(mRequestType, NetUtils.SERVER_RETURN_ERROR, 0, failedPrompt)
                                        .sendToTarget();
                        }
                    }
                }
                break;
                case RequestTypeConstant.RETURN_JSON_MESSAGE: {
                    Zlog.ii("lxm ss httppost  initJson 5:" + response);
                    if (mHandler != null) {
                        if (response != null) {
                            mHandler.obtainMessage(mRequestType, NetUtils.SERVER_RETURN_OK, 0, response)
                                    .sendToTarget();
                        } else {
                            mHandler.obtainMessage(mRequestType, NetUtils.SERVER_RETURN_ERROR, 0, failedPrompt)
                                    .sendToTarget();
                        }
                    }
                }
                break;

                default:
                    break;
            }

        }
    }

    /**
     * 解析返回json的message
     *
     * @return
     */
    private synchronized HttpJsonResult parseJsonData(String message) {
        if (message == null) {
            return null;
        }
        HttpJsonResult httpJsonResult = null ;
        TypeToken<HttpJsonResult> typeToken = new TypeToken<HttpJsonResult>() {
        };
        try {
            httpJsonResult  = (HttpJsonResult) GsonUtils.getInstance().parseJson(message,typeToken);
        }catch (JsonSyntaxException e) {
            e.printStackTrace();
            Zlog.i("lxm ss httppost parseJsonData Exception:" + mRequestType + e.getMessage());
        }

        return httpJsonResult;

    }

//    private void saveLocalData(boolean isSucceed,String message) {
//
//        //处理保存数据
//        switch(mRequestType) {
//
//            case RequestTypeConstant.REQUEST_TYPE_GET_COUNTRYCODE:{
//
//            }break;
//            case RequestTypeConstant.REQUEST_TYPE_GET_COUNTRYLIST:{
//
//            }break;
//            case RequestTypeConstant.REQUEST_TYPE_GET_SHOPCATEGORY:{
//                PreferenceUtils.getInstance(mContext).setStringValue(PreferenceUtils.SHOP_CATEGORY, message);
//            }break;
//            case RequestTypeConstant.REQUEST_TYPE_GET_HOTSEARCH:{
//
//            }break;
//            case RequestTypeConstant.REQUEST_TYPE_GET_UPDATEVERSION:{
//
//            }break;
//            default:
//                break;
//        }
//
//    }
//

    /**
     * 解析返回json的message
     *
     * @return
     */
    private synchronized T parseJsonMessage(String message) {
        if (message == null) {
            return null;
        }
        T o = null;
        try {
            if (mHandler != null) {
                o = (T) GsonUtils.getInstance().parseJson(message,typeToken);
                Zlog.ii("lxm ss volley parseJsonMessage 2:"+ o.toString());
            }
            //处理保存数据
            switch(mRequestType) {

                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Zlog.i("lxm ss httppost parseJsonReq Exception:" + mRequestType + e.getMessage());
        }
        return o;
    }



}
