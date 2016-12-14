package com.lxm.ss.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by lxm on 2016/11/18.
 */

public class GsonUtils<T>  {

    private static  GsonUtils gsonUtils ;
    private Gson gson = new Gson();

    public static GsonUtils getInstance() {
        if (gsonUtils == null) {
            gsonUtils = new GsonUtils();
        }
        return gsonUtils ;
    }

    public T parseJson(String message, TypeToken<T> typeToken) {
        T t = gson.fromJson(message, typeToken.getType());
        if(t != null) {
            Zlog.ii("lxm volley parseJson :"+ t.toString());
        }
        return t ;
    }
}
