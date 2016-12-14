package com.lxm.ss.http;//package club.fromfactory.http;
//
//import java.util.Map;
//
//import okhttp3.Call;
//import android.os.Handler;
//import android.text.TextUtils;
//
//public class MyStringCallback<T> extends StringCallback {
//
//  private int mRequestType;// 请求类型
//  private int returnType; // 是否需要进一步解析
//  private Handler mHandler;
//  private TypeReference<T> typeReference;// 返回数据类型
//  private String url;// 请求地址
//  private String failedPrompt = "服务器连接失败";
//
//  public MyStringCallback(int mRequestType, int returnType, Handler mHandler,
//      TypeReference<T> typeReference, String url) {
//    MyStringCallback.this.mRequestType = mRequestType;
//    MyStringCallback.this.returnType = returnType;
//    MyStringCallback.this.mHandler = mHandler;
//    MyStringCallback.this.typeReference = typeReference;
//    MyStringCallback.this.url = url;
//  }
//
//  @Override
//  public void onError(Call call, Exception e) {
//    e.printStackTrace();
//    Zlog.ii("httppost Exception:url:" + url + "; error:" + e.getMessage());
//    mHandler.obtainMessage(mRequestType, NetUtil.SERVER_RETURN_ERROR, 0, "服务器内部错误").sendToTarget();
//    Map<String, String> infos =
//        CrashHandler.getInstance().collectDeviceInfo(WjtvApplication.getInstance());
//    infos.put("httpurl", url);
//    CrashHandler.getInstance().saveCrashInfo2File(e, infos);
//  }
//
//  @Override
//  public void onResponse(String response) {
//    Zlog.ii("httppost:" + response);
//    initJson(response);
//  }
//
//  /**
//   * 解析
//   *
//   * @param mRequestType
//   * @param resstr
//   * @param returnType
//   */
//  public synchronized void initJson(String response) {
//    Zlog.ii("httppost initJson 1:" + response);
//
//    Object obj = null;
//    if (TextUtils.isEmpty(response)) {
//      if (mHandler != null) {
//        mHandler.obtainMessage(mRequestType, NetUtil.SERVER_RETURN_NULL, 0, failedPrompt)
//            .sendToTarget();
//      }
//    } else {
//      String message = parseJsonResponse(response);
//
//      if (TextUtils.isEmpty(message)) {
//        // TODO 方便调试，需删除
//        // mHandler.obtainMessage(mRequestType, NetUtil.SERVER_RETURN_ERROR, 0, "接口返回为空")
//        // .sendToTarget();
//        return;
//      }
//
//      switch (returnType) {
//        case RequestTypeConstant.RETURN_INITJSON_DATA: {
//          Zlog.ii("httppost  initJson 2:" + response);
//          obj = parseJsonMessage(message);
//          if (obj != null) {
//            Zlog.ii("httppost  initJson 3:" + obj);
//            if (mHandler != null) {
//              mHandler.obtainMessage(mRequestType, NetUtil.SERVER_RETURN_OK, 0, obj).sendToTarget();
//            }
//          } else {
//            if (mHandler != null) {
//              if (message != null) {
//                mHandler.obtainMessage(mRequestType, NetUtil.SERVER_RETURN_ERROR, 0, message)
//                    .sendToTarget();
//              } else {
//                mHandler.obtainMessage(mRequestType, NetUtil.SERVER_RETURN_ERROR, 0, failedPrompt)
//                    .sendToTarget();
//              }
//            }
//          }
//        }
//          break;
//        case RequestTypeConstant.RETURN_JSON_MESSAGE: {
//          if (mHandler != null) {
//            if (message != null) {
//              mHandler.obtainMessage(mRequestType, NetUtil.SERVER_RETURN_OK, 0, message)
//                  .sendToTarget();
//            } else {
//              mHandler.obtainMessage(mRequestType, NetUtil.SERVER_RETURN_ERROR, 0, failedPrompt)
//                  .sendToTarget();
//            }
//          }
//        }
//          break;
//
//        default:
//          break;
//      }
//
//    }
//  }
//
//  /**
//   * 初步解析
//   *
//   * @param mRequestType
//   * @param response
//   */
//  private String parseJsonResponse(String response) {
//    JsonResult jResult = null;
//    try {
//      jResult = JSONObject.parseObject(response, JsonResult.class);
//    } catch (Exception e) {
//      e.printStackTrace();
//      Zlog.ii("httppost Exception :" + e.getMessage());
//    }
//
//    if (jResult == null) {
//      if (mHandler != null) {
//        mHandler.obtainMessage(mRequestType, NetUtil.SERVER_RETURN_ERROR, 0, failedPrompt)
//            .sendToTarget();
//      }
//      return null;
//    }
//
//    // 未登陆的情况
//    if ("unlogin".equals(jResult.getMessage())) {
//      if (mHandler != null) {
//        // mHandler.sendEmptyMessage(REQUEST_TYPE_LOGIN_EXCEPTION);
//        // mHandler = null;
//        // Intent intent = new
//        // Intent(LoginStateChangeReceiver.LOGIN_STATE_CHANGE);
//        // context.sendBroadcast(intent);
//      }
//      return null;
//    }
//
//    // 返回错误的情况 false
//    if (!TextUtils.equals(jResult.getStatus(), RequestTypeConstant.STR_SERVER_RETURN_OK)) {
//      if (mHandler != null) {
//        mHandler.obtainMessage(mRequestType, NetUtil.SERVER_RETURN_ERROR, 0, jResult.getMessage())
//            .sendToTarget();
//      }
//      return null;
//    }
//
//    return jResult.getMessage();
//  }
//
//  /**
//   * 解析返回json的message
//   *
//   * @param <T>
//   *
//   * @param jResult
//   * @param type
//   * @return
//   */
//  private synchronized T parseJsonMessage(String message) {
//    if (message == null) {
//      return null;
//    }
//    T o = null;
//    try {
//      o = JSONObject.parseObject(message, typeReference);
//      if (mRequestType == RequestTypeConstant.REQUEST_TYPE_CITY_LIST) {
//        PreferenceWithExpiresUtils.getInstance().setStringValue(PreferenceName.ALL_ZONE,
//            WjtvApplication.getInstance(), message, Expires.month);
//      } else if (mRequestType == RequestTypeConstant.REQUEST_TYPE_INDUSTRY_DATA) {
//        PreferenceWithExpiresUtils.getInstance().setStringValue(PreferenceName.CATEGORIES,
//          WjtvApplication.getInstance(), message, Expires.month);
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//      Zlog.i("httpPost parseJsonReq Exception:" + e.getMessage());
//    }
//    return o;
//  }
//
//  /**
//   * 将字符串转成unicode
//   *
//   * @param str 待转字符串
//   * @return unicode字符串
//   */
//  public String convert(String str) {
//    str = (str == null ? "" : str);
//    String tmp;
//    StringBuffer sb = new StringBuffer(1000);
//    char c;
//    int i, j;
//    sb.setLength(0);
//    for (i = 0; i < str.length(); i++) {
//      c = str.charAt(i);
//      sb.append("\\u");
//      j = (c >>> 8); // 取出高8位
//      tmp = Integer.toHexString(j);
//      if (tmp.length() == 1) sb.append("0");
//      sb.append(tmp);
//      j = (c & 0xFF); // 取出低8位
//      tmp = Integer.toHexString(j);
//      if (tmp.length() == 1) sb.append("0");
//      sb.append(tmp);
//
//    }
//    return (new String(sb));
//  }
//}
