package com.lxm.ss.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * Created by lxm on 2016/11/9.
 */

public class NetUtils {

    // 断网提示用户需要加载的本地Html页面的URL
    public static final String NETWORK_ERROR_PAGE_URL = "file:///android_asset/InternetError.html";

    public static final int SERVER_RETURN_NULL = 100;
    public static final int SERVER_RETURN_ERROR = 101;
    public static final int SERVER_RETURN_OK = 102;

    public static final int NET_CONNECT_TYPE_NONE = -1;
    public static final int NET_CONNECT_TYPE_WIFI = 1;
    public static final int NET_CONNECT_TYPE_MOBILE = 2;

    // APP主页的网址；
    /**
     * 预发布环境
     * @"http://app.fromfactory.club/"
    #define COOKIE_DOMAIN @".fromfactory.club"
    //#define HOST @"http://112.124.99.208:8000/"
    //#define HOST @"http://10.135.198.206:8000/" // za
    //#define HOST @"http://47.88.85.20:8000/"
    //#define HOST @"http://app.fromfactory.club:9000/"
     */
//    public static final String APP_MAIN_URL = "http://47.88.85.20:8000/";
//    public static final String APP_MAIN_URL = "http://10.135.198.206:8000/";
//    public static final String APP_MAIN_URL = "http://112.124.99.208:8000/";
    /**
     * 线上
     */

    public static final String APP_MAIN_URL = "http://app.fromfactory.club/";


    /**
     * 检查网络
     *
     * @param context
     * @return 返回是否有网
     */
    public static boolean checkNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return true;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                int type = mNetworkInfo.getType();
                switch (type) {
                    case ConnectivityManager.TYPE_WIFI:
                        return NET_CONNECT_TYPE_WIFI;
                    case ConnectivityManager.TYPE_MOBILE:
                        return NET_CONNECT_TYPE_MOBILE;
                    default:
                        break;
                }
            }
        }
        return NET_CONNECT_TYPE_NONE;
    }

}
