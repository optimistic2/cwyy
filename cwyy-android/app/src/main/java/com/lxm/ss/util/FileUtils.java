package com.lxm.ss.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.text.TextUtils;

import com.lxm.ss.pojo.RoomSizeInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


// 普通文件的存储方式
public class FileUtils {
  public static final int REQUEST_TYPE_OPEN_FILE = 500001; // 阅读文件 2.1.7
  private String SDPATH;

  public static FileUtils instance;

  // 单例模式 获取一个唯一的处理类，避免每个地方用到都新建一下，浪费空间
  public static FileUtils getInstance() {
    if (instance == null) {
      instance = new FileUtils();
    }
    return instance;
  }

  // 获取sd卡上存储文件的路径
  public String getSDPATH() {
    return SDPATH;
  }

  // 获取sd卡上存储文件的路径
  public String getSDPATHWork() {
    return SDPATH + "yunsj/club/";
  }

  // 获取sd卡上的可用余额
  public int GetSDAvailableSpare() {
    String sdcard = Environment.getExternalStorageDirectory().getPath();

    File file = new File(sdcard);
    StatFs statFs = new StatFs(file.getPath());
    int availableSpare = (int) (statFs.getBlockSize() * ((long) statFs.getAvailableBlocks() - 4));
    return availableSpare;
  }

  // 删除指定目录下的所有文件
  private int DeleteAllFiles(File root) {
    File files[] = root.listFiles();
    if (files != null) {
      for (File f : files) {
        if (f.isDirectory()) {
          DeleteAllFiles(f);
        } else {
          System.out.println(f);
          if (f.exists()) {
            if (f.delete() == false) {
              return -1;
            }
          }
        }
      }
    }
    return 0;
  }

  // 删除本应用存储目录下的所有文件
  public int CleanWorkingDir() {
    File root = new File(Environment.getExternalStorageDirectory().getPath() + "/yunsj/club/");
    if (DeleteAllFiles(root) != 0) {
      return -1;
    }
    return 0;
  }

  // sd卡的根目录
  public FileUtils() {
    // /SDCARD
    if (Environment.getExternalStorageDirectory() != null
        && Environment.getExternalStorageDirectory().exists()) {
      SDPATH = Environment.getExternalStorageDirectory() + "/";
      try {
        createSDDir("yunsj/club");
        Zlog.ii("lxm :1111");
      } catch (Exception e) {
        Zlog.ii("lxm :1122");
      }
    } else {
      Zlog.ii("lxm :1123");
      SDPATH = "/storage/sdcard0/";
//      SDPATH = "/storage/emulated/0/";
      File ff = new File(SDPATH);
      if (ff.exists() == false) {
        SDPATH = "";
        Zlog.ii("lxm :1124");
      }
    }
  }

  /**
   * 在SD卡上创建目录
   */
  public File createSDDir(String dirName) {
    if (SDPATH.equals("")) {
      return null;
    }
    System.out.println("SDPATH:" + SDPATH);
    File dir = new File(SDPATH + dirName);
    boolean ret = dir.mkdirs();
    if (ret == false) {
      return null;
    }

    return dir;
  }

  /***
   * 在SD卡上创建文件
   *
   * @throws IOException
   */
  public File createSDFile(String fileName) throws IOException {
    System.out.println("createSDFile:" + fileName);
    File file = new File(SDPATH + "yunsj/club/" + fileName);
    file.createNewFile();


    return file;
  }


  /**
   * 判断SD卡上的文件夹是否存在，这里指定文件名
   */
  public boolean isFileExist(String fileName) {
    File file = new File(SDPATH + "yunsj/club/" + fileName);
    return file.exists();
  }

  /**
   * 判断SD卡上的文件夹是否存在，这里指定全路径
   */
  public boolean isFileExistOnfull(String fileName) {
    File file = new File(fileName);
    return file.exists();
  }

  /**
   * 将一个InputStream里面的数据写入到SD卡中
   */
  public File write2SDFromInput(String path, String fileName, InputStream input) {
    File file = null;
    OutputStream output = null;
    try {
      // 先创建目录
      createSDDir(path);
      // 拼装文件全路径
      file = createSDFile(path + fileName);
      output = new FileOutputStream(file);
      byte buffer[] = new byte[4 * 1024];
      while ((input.read(buffer)) != -1) {
        output.write(buffer);
      }
      output.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        output.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return file;
  }

  private Handler handler ;
  private Context context ;
  public void loadFile(Handler handler, Context context , final String file) {
    this.handler = handler ;
    this.context = context ;
    new Thread(new Runnable() {

      @Override
      public void run() {

        loadFileFromNet(file);

      }
    }).start();
  }

  private void loadFileFromNet(String urlString) {
//    String urlString = "http://public.dhe.ibm.com/common/ssi/ecm/en/wsd14109usen/WSD14109USEN.PDF";
    String[] name = urlString.split("/");
    FileUtils mfile = new FileUtils();
    String path = mfile.getSDPATHWork();

    if(FileUtils.getInstance().isFileExist(name[name.length - 1])){
      Zlog.ii("lxm loadFileFromNet :5"+"--------------local:"+name[name.length - 1]);

      handler.obtainMessage(REQUEST_TYPE_OPEN_FILE, path + name[name.length - 1]).sendToTarget();
    }else{
      URL url;
      InputStream i = null;
      File file = new File(path + name[name.length - 1]);

      Looper.prepare();
      final ProgressDialog pd = new ProgressDialog(context);
      pd.setCanceledOnTouchOutside(false);
      pd.setMessage("正在下载...");
      pd.show();
      try {
          file.createNewFile();
          url = new URL(urlString);
          i = (InputStream) url.getContent();

          // 打开连接
          URLConnection con = url.openConnection();
          //获得文件的长度
          int contentLength = con.getContentLength();
         Zlog.ii("长度 :"+contentLength);
          // 输入流
          InputStream is = con.getInputStream();
          // 1K的数据缓冲
          byte[] bs = new byte[1024];
          // 读取到的数据长度
          int len;
          // 输出的文件流
          OutputStream os = new FileOutputStream(file);
          // 开始读取
          while ((len = is.read(bs)) != -1) {
              os.write(bs, 0, len);
          }
          // 完毕，关闭所有链接
          os.close();
          is.close();
      } catch (MalformedURLException e1) {
          e1.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
      pd.dismiss();
      Looper.loop();
      handler.obtainMessage(REQUEST_TYPE_OPEN_FILE, file.getPath()).sendToTarget();
    }


    //    try {
//      URL url = new URL(urlString);
//      HttpURLConnection connection = (HttpURLConnection)
//          url.openConnection();
//      connection.setRequestMethod("GET");
//      connection.setDoInput(true);
//      connection.setDoOutput(true);
//      connection.setUseCaches(false);
//      connection.setConnectTimeout(5000);
//      connection.setReadTimeout(5000);
//      //实现连接
//      connection.connect();
//
//      Zlog.ii("lxm loadFileFromNet :5"+"connection.getResponseCode()="+connection.getResponseCode());
//      if (connection.getResponseCode() == 200) {
//        InputStream is = connection.getInputStream();
//        //以下为下载操作
//        byte[] arr = new byte[1];
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        BufferedOutputStream bos = new BufferedOutputStream(baos);
//        int n = is.read(arr);
//        while (n > 0) {
//          bos.write(arr);
//          n = is.read(arr);
//        }
//        bos.close();
//        FileUtils mfile = new FileUtils();
//        String path = mfile.getSDPATHWork()
//            + "/download/";
//        String[] name = urlString.split("/");
//        path = path + name[name.length - 1];
//
//        Zlog.ii("lxm loadFileFromNet :2"+"name="+name);
//        Zlog.ii("lxm loadFileFromNet :3"+"path="+path);
//        File file = new File(path);
//        file.createNewFile();
//        FileOutputStream fos = new FileOutputStream(file);
//        fos.write(baos.toByteArray());
//        fos.close();
//        //关闭网络连接
//        connection.disconnect();
//        Zlog.ii("lxm loadFileFromNet :1"+"下载完成");
//              if (file.exists()) {
//                Zlog.ii("lxm loadFileFromNet :1"+"打开");
//                Uri path1 = Uri.fromFile(file);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(path1, "application/pdf");
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                try {
//                    context.startActivity(intent);
//                }
//                catch (ActivityNotFoundException e) {
//                  Zlog.ii("lxm loadFileFromNet :6"+"打开失败");
//                }
//            }
//      }
//    } catch (IOException e) {
//      Zlog.ii("lxm loadFileFromNet :4"+e.getMessage());
//    }
  }

  public String saveMyBitmap(Bitmap bitp, String fileName) throws IOException {
    return saveMyBitmap(bitp, fileName, null);
  }

  public String saveMyBitmap(Bitmap bitp, String fileName, Context context) throws IOException {
    FileUtils mfile = new FileUtils();
    String filePath = null;
    if (TextUtils.isEmpty(fileName)) {
      fileName = getPhotoFileName();
    }
    File f = new File(mfile.getSDPATHWork() + fileName);
    f.createNewFile();
    FileOutputStream fOut = null;
    try {
      fOut = new FileOutputStream(f);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    bitp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
    filePath = f.getPath();
    try {
      fOut.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      fOut.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // 最后通知图库更新
    if (context != null)
      context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"
          + f.getAbsolutePath())));
    return filePath;
  }

  public String getPhotoFileName() {
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
    return dateFormat.format(date) + ".jpg";
  }

  // 获取sd相关区域的容量 如总容量、剩余可用的容量以及本app工作区的容量
  public RoomSizeInfo getSdTotalSize() {
    RoomSizeInfo room = new RoomSizeInfo();

    // 取得sdcard文件路径
    File pathFile = Environment.getExternalStorageDirectory();

    StatFs statfs = new StatFs(pathFile.getPath());

    // 获取SDCard上BLOCK总数
    long nTotalBlocks = statfs.getBlockCount();

    // 获取SDCard上每个block的SIZE
    long nBlocSize = statfs.getBlockSize();

    // 获取可供程序使用的Block的数量
    long nAvailaBlock = statfs.getAvailableBlocks();

    // 获取剩下的所有Block的数量(包括预留的一般程序无法使用的块)
    // long nFreeBlock = statfs.getFreeBlocks();

    // 计算SDCard 总容量大小MB
    long nSDTotalSize = nTotalBlocks * nBlocSize / 1024 / 1024;

    // 计算 SDCard 剩余大小MB
    long nSDFreeSize = nAvailaBlock * nBlocSize / 1024 / 1024;
    room.totalsize = nSDTotalSize;
    room.freesize = nSDFreeSize;
    File fil = new File(pathFile.getPath() + "/" + "yunsj/club/");
    try {
      room.worksize = getFolderSize(fil);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return room;
  }


  /**
   * 获取文件夹大小
   *
   * @param file File实例
   * @return long 单位为M
   * @throws Exception
   */
  public static long getFolderSize(File file) throws Exception {
    long size = 0;
    File[] fileList = file.listFiles();
    for (int i = 0; i < fileList.length; i++) {
      if (fileList[i].isDirectory()) {
        size = size + getFolderSize(fileList[i]);
      } else {
        size = size + fileList[i].length();
      }
    }
    return size / 1048576;
  }



  /**
   * 删除指定目录下文件及目录
   *
   * @param deleteThisPath
   * @param
   * @return
   */
  public void deleteFolderFile(String filePath, boolean deleteThisPath) throws IOException {
    if (!TextUtils.isEmpty(filePath)) {
      File file = new File(filePath);

      if (file.isDirectory()) {// 处理目录
        File files[] = file.listFiles();
        for (int i = 0; i < files.length; i++) {
          deleteFolderFile(files[i].getAbsolutePath(), true);
        }
      }
      if (deleteThisPath) {
        if (!file.isDirectory()) {// 如果是文件，删除
          file.delete();
        } else {// 目录
          if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
            file.delete();
          }
        }
      }
    }
  }

  /**
   * 读取表情配置文件
   *
   * @param context
   * @return
   */
  public static List<String> getEmojiFile(Context context) {
    try {
      List<String> list = new ArrayList<String>();
      InputStream in = context.getResources().getAssets().open("emoji");
      BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
      String str = null;
      while ((str = br.readLine()) != null) {
        list.add(str);
      }

      return list;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  // 从assert配置文件中读取数据到list中
  public static List<String> getGifFile(Context context) {
    try {
      List<String> list = new ArrayList<String>();
      InputStream in = context.getResources().getAssets().open("gif");
      BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
      String str = null;
      while ((str = br.readLine()) != null) {
        list.add(str);
      }

      return list;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
