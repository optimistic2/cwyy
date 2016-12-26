package com.lxm.ss;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.lxm.ss.util.DateUtil;
import com.lxm.ss.util.FileUtils;
import com.lxm.ss.util.Zlog;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author user
 *
 */
public class CrashHandler implements UncaughtExceptionHandler {

  public static final String TAG = "CrashHandler";

  // 系统默认的UncaughtException处理类
  private UncaughtExceptionHandler mDefaultHandler;
  // CrashHandler实例
  private static CrashHandler INSTANCE = new CrashHandler();
  // 程序的Context对象
  private Context mContext;
  // 用来存储设备信息和异常信息
//  private Map<String, String> infos = new HashMap<String, String>();

  // 用于格式化日期,作为日志文件名的一部分

  private String logName;

  /** 保证只有一个CrashHandler实例 */
  private CrashHandler() {}

  /** 获取CrashHandler实例 ,单例模式 */
  public static CrashHandler getInstance() {
    return INSTANCE;
  }

  /**
   * 初始化
   *
   * @param context
   */
  public void init(Context context) {
    mContext = context;

    // 获取系统默认的UncaughtException处理器
    mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    // 设置该CrashHandler为程序的默认处理器
    Thread.setDefaultUncaughtExceptionHandler(this);
  }

  /**
   * 当UncaughtException发生时会转入该函数来处理
   */
  @Override
  public void uncaughtException(Thread thread, Throwable ex) {
    if (!handleException(ex) && mDefaultHandler != null) {
      // 如果用户没有处理则让系统默认的异常处理器来处理
      mDefaultHandler.uncaughtException(thread, ex);

    } else {
      try {
        thread.sleep(3000);
      } catch (InterruptedException e) {
        Log.e(TAG, "error : ", e);
      }
    }
    CwyyApplication.finishAllAndExit();
  }

  /**
   * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
   * 很抱歉,程序出现异常,即将退出
   * @param ex
   * @return true:如果处理了该异常信息;否则返回false.
   */
  private boolean handleException(Throwable ex) {
    if (ex == null) {
      return false;
    }
    // 使用Toast来显示异常信息
    new Thread() {
      @Override
      public void run() {
        Looper.prepare();
        Toast.makeText(mContext, "We are very sorry but the app crashes…", Toast.LENGTH_LONG).show();
        Looper.loop();
      }
    }.start();

    // 收集设备参数信息
    Map<String, String> infos = collectDeviceInfo(mContext);
    // 保存日志文件
    logName = saveCrashInfo2File(ex, infos);

//     上传至服务器
//     if (logName != null) {
//     String path = FileUtils.getInstance().getSDPATHWork()+"crash/" +
//     logName;
//
//     File file = new File(path);
//
//     postReport(file);
//     }

    return true;
  }

  /**
   * 收集设备参数信息
   *
   * @param ctx
   */
  public Map<String, String> collectDeviceInfo(Context ctx) {
    Map<String, String> infos = new HashMap<String, String>();
    try {
      PackageManager pm = ctx.getPackageManager();
      PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
      if (pi != null) {
        String versionName = pi.versionName == null ? "null" : pi.versionName;
        String versionCode = pi.versionCode + "";
        infos.put("versionName", versionName);
        infos.put("versionCode", versionCode);
      }
    } catch (NameNotFoundException e) {
      Log.e(TAG, "an error occured when collect package info", e);
    }
    Field[] fields = Build.class.getDeclaredFields();
    for (Field field : fields) {
      try {
        field.setAccessible(true);
        infos.put(field.getName(), field.get(null).toString());
        Log.d(TAG, field.getName() + " : " + field.get(null));
      } catch (Exception e) {
        Log.e(TAG, "an error occured when collect crash info", e);
      }
    }
    return infos;
  }

  /**
   * 保存错误信息到文件中
   *
   * @param ex
   * @return 返回文件名称,便于将文件传送到服务器
   */
  public String saveCrashInfo2File(Throwable ex, Map<String, String> infos) {

    StringBuffer sb = new StringBuffer();
    for (Map.Entry<String, String> entry : infos.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      sb.append(key + "=" + value + "\n");
    }

    Writer writer = new StringWriter();
    PrintWriter printWriter = new PrintWriter(writer);
    ex.printStackTrace(printWriter);
    Throwable cause = ex.getCause();
    while (cause != null) {
      cause.printStackTrace(printWriter);
      cause = cause.getCause();
    }
    printWriter.close();
    String result = writer.toString();
    sb.append(result);
    try {
      long timestamp = System.currentTimeMillis();
      String time = DateUtil.long2string(timestamp, DateUtil.YYYY_MM_DD_HH_MM);

      String fileName = "crash-" + time + "-" + timestamp + ".txt";
      if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        String path = FileUtils.getInstance().getSDPATHWork() + "crash/";
        File dir = new File(path);
        if (!dir.exists()) {
          dir.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(path + fileName);
        fos.write(sb.toString().getBytes());
        Zlog.ii("lxm :1125");
        // 发送给开发人员
        postReport(fileName, path + fileName);

        fos.close();
      }
      return fileName;
    } catch (Exception e) {
      Zlog.ii("lxm :1126");
      Zlog.ii("an error occured while writing file..."+ e);
    }
    return null;
  }

  // 使用HTTP Post 发送错误报告到服务器
  // 在上传的时候还可以将该app的version，该手机的机型等信息一并发送的服务器，
  // Android的兼容性众所周知，所以可能错误不是每个手机都会报错，还是有针对性的去debug比较好
  private void postReport(String fileName, String resFile) {

    if (!new File(resFile).exists()) {
       Toast.makeText(mContext, "日志文件不存在！", Toast.LENGTH_SHORT).show();
      Zlog.i("CrashHandler : ", "lxm  vgsgds:" + "日志文件不存在！");
      return;
    }
    // 闪退日志上传到后台

     Zlog.i("CrashHandler : ", "lxm  vgsgds:" + fileName);
//     FileTranHttp fileTranHttp = new FileTranHttp();
//     fileTranHttp.uploadImageFile(mContext, handler, "txt", resFile, "");
  }
}
