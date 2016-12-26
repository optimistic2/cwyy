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

    /**
     * 线上
     */

    public static final String APP_MAIN_URL = "http://optimistic2.55555.io:32162/";

    /**
     * 	  新增、修改字典
     */
    public static final String CWYY_GET_ADDGIC = APP_MAIN_URL + "dic/addDic" ;
    /**
     * 根据分页信息和条件分页查询字典列表
     */
    public static final String CWYY_GET_PAGEDIC = APP_MAIN_URL + "dic/pageDic" ;
    /**
     *	  根据字典id查询字典
     */
    public static final String CWYY_GET_DICBYID = APP_MAIN_URL + "dic/getDicById" ;
    /**
     *根据id批量删除
     */
    public static final String CWYY_GET_DELETEDICBYIDS = APP_MAIN_URL + "dic/deleteDicByIds" ;
    /**
     *
     */
    public static final String CWYY_GET_= APP_MAIN_URL + "" ;


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
