package com.lxm.ss.util;

import android.content.Context;
import android.content.SharedPreferences;

// 键值对的存储方式
public class PreferenceUtils {
  Context context;
  private SharedPreferences mPref;
  private static String share_name = "itemSpref";// yunsj_club

  public static final String SHOP_CATEGORY = "content";//
  public static final String HOT_SEARCH = "hotSearch";//热门搜索信息
  public static final String COUNTTY_CODE = "country";//国家列表信息
  public static final String COUNTTY_CODE_LOCAL = "country_local";//本地ip的国家信息
  public static final String PUSH_URL = "parseUrl";//通知的链接网址

  public static PreferenceUtils instance;

  // 单例模式
  public static PreferenceUtils getInstance(Context context) {
    if (instance == null) {
      instance = new PreferenceUtils(context);
      instance.setConfigName(share_name);
    }
    return instance;
  }

  public PreferenceUtils(Context context) {
    this.context = context;
  }

  // 设置配置文件的名字
  public void setConfigName(String configname) {
    mPref = context.getSharedPreferences(configname, Context.MODE_PRIVATE);
  }

  // 构造函数，顺便就设置了配置文件的名字
  public PreferenceUtils(Context context, String configname) {
    this.context = context;
    setConfigName(configname);
  }

  // 获取int类型的值
  public int getIntValue(String key, int defaultValue) {
    return mPref.getInt(key, defaultValue);
  }

  // 获取string类型的值
  public String getStringValue(String key, String defaultValue) {
    return mPref.getString(key, defaultValue);
  }
  // 获取string类型的值
  public String getStringValue(String key) {
    return mPref.getString(key, "");
  }

  // 获取float类型的值
  public float getFloatValue(String key, float defaultValue) {
    return mPref.getFloat(key, defaultValue);
  }

  // 获取long类型的值
  public long getLongValue(String key, long defaultValue) {
    return mPref.getLong(key, defaultValue);
  }

  // 获取Boolean类型的值
  public Boolean getBooleanValue(String key, boolean defaultValue) {
    return mPref.getBoolean(key, defaultValue);
  }

  // 设置Boolean类型的值到配置文件
  public void setIntValue(String key, int value) {
    mPref.edit().putInt(key, value).commit();
  }

  // 设置String类型的值到配置文件
  public void setStringValue(String key, String value) {
    mPref.edit().putString(key, value).commit();
  }

  // 设置float类型的值到配置文件
  public void setFloatValue(String key, float value) {
    mPref.edit().putFloat(key, value).commit();
  }

  // 设置long类型的值到配置文件
  public void setLongValue(String key, long value) {
    mPref.edit().putLong(key, value).commit();
  }

  // 设置boolean类型的值到配置文件
  public void setBooleanValue(String key, boolean value) {
    mPref.edit().putBoolean(key, value).commit();
  }

}
