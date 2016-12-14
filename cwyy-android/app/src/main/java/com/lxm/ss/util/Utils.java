package com.lxm.ss.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import com.lxm.ss.CwyyApplication;
import com.lxm.ss.http.HttpReturnListener;
import com.lxm.ss.http.NetUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by lxm on 2016/11/9.
 */

public class Utils {


    /*
   * 获取当前程序的版本号
   */
    public static int getApplicationVersionCode(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Integer.MAX_VALUE;
    }

    /**
     *请求商品类别标签，sendemptymessage 3
      * @return
     */
    public static String getAndroidId () {
        return  Settings.Secure.getString(CwyyApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceId() {
        TelephonyManager telephonyManager;
        telephonyManager =
                (TelephonyManager)CwyyApplication.getInstance().getSystemService( Context.TELEPHONY_SERVICE );
        return telephonyManager.getDeviceId();
    }


    public static String getApplicationVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException ex) {
        } catch (Exception e) {
        }
        return null;
    }
    /**
     * 获取屏幕的宽
     *
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int sWidth = display.getWidth();
        return sWidth;
    }

    /**
     * 获取屏宽高
     *
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int sHeight = display.getHeight();
        return sHeight;
    }

    /**
     * 处理http返回数据
     *
     * @param msg
     * @param context
     * @param l
     */
    public static void doHttpRetrue(Message msg, Context context, HttpReturnListener l) {
        switch (msg.arg1) {
            case NetUtils.SERVER_RETURN_ERROR:
                l.onError(msg, context);
                break;

            case NetUtils.SERVER_RETURN_NULL:
                l.onNull(msg, context);
                break;

            case NetUtils.SERVER_RETURN_OK:
                l.onSuccess(msg, context);
                break;

            default:
                break;
        }
    }

    /**
     * 获取国家码
     */
    public static String getCountryZipCode(Context context) {
        String CountryID = "";
//        String CountryZipCode = "";
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Configuration config = context.getResources().getConfiguration();
        String countryConfig  = config.locale.getCountry();
        CountryID = manager.getSimCountryIso().toUpperCase();
        String country = Locale.getDefault().getCountry();
        Zlog.ii("lxm ss:CountryID--->>>" + CountryID + "   " +country  + "   " + countryConfig);
//        String[] rl = context.getResources().getStringArray(R.array.CountryCodes);
//        for (int i = 0; i < rl.length; i++) {
//            String[] g = rl[i].split(",");
//            if (g[1].trim().equals(CountryID.trim())) {
//                CountryZipCode = g[0];
//                break;
//            }
//        }
        return country;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public  static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
