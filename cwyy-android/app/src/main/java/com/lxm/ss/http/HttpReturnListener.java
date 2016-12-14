package com.lxm.ss.http;

import android.content.Context;
import android.os.Message;
import android.widget.Toast;

/**
 * 处理http返回数据Listener
 * 
 * @author Administrator
 * 
 */
public abstract class HttpReturnListener {
  public void onError(Message msg, Context context) {
    if (msg.obj == null) return;
    String objs = (String) msg.obj;
    if (objs.equals("[]")) return;
    Toast.makeText(context, objs, Toast.LENGTH_SHORT).show();
  }

  public void onNull(Message msg, Context context) {
    if (msg.obj == null) return;
    String objs = (String) msg.obj;
    if (objs.equals("[]")) return;
    Toast.makeText(context, objs, Toast.LENGTH_SHORT).show();
  }

  public abstract void onSuccess(Message msg, Context context);
}