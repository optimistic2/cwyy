package com.lxm.ss.util;

import android.util.Log;

public class Zlog {
	public static boolean isToast = true ;
	public static boolean isDebug = true;
	private static final String TAG = "wujietr";

	// 
	public static void i(String msg) {
		if (isDebug && msg!=null)
			Log.i(TAG, "wujietr:"+msg);
	}

	public static void d(String msg) {
		if (isDebug && msg!=null)
			Log.d(TAG,  "wujietr:"+msg);
	}

	public static void e(String msg) {
		if (isDebug && msg!=null)
			Log.e(TAG,  "wujietr:"+msg);
	}

	public static void v(String msg) {
		if (isDebug && msg!=null)
			Log.v(TAG, msg);
	}
	//
	//*
	public static void i(Class<?> _class, String msg){
		if (isDebug && msg!=null)
			Log.i(_class.getName(),  "wujietr:"+msg);
	}
	//*/
	public static void d(Class<?> _class, String msg){
		if (isDebug && msg!=null)
			Log.i(_class.getName(),  "wujietr:"+msg);
	}
	public static void e(Class<?> _class, String msg){
		if (isDebug && msg!=null)
			Log.i(_class.getName(),  "wujietr:"+msg);
	}
	public static void v(Class<?> _class, String msg){
		if (isDebug && msg!=null)
			Log.i(_class.getName(),  "wujietr:"+msg);
	}
	//
	public static void i(String tag, String msg) {
		if (isDebug && msg!=null)
			Log.i(tag, "wujietr:"+ msg);
	}
	public static void ii( String msg) {
		if (isDebug && msg!=null)
			Log.i(TAG, "wujietr:"+ msg);
	}

	public static void d(String tag, String msg) {
		if (isDebug && msg!=null)
			Log.d(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isDebug && msg!=null)
			Log.e(tag,  "wujietr:"+msg);
	}

	public static void v(String tag, String msg) {
		if (isDebug && msg!=null)
			Log.v(tag,  "wujietr:"+msg);
	}
}
