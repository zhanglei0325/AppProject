package com.lei.util;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

public class UtilsHelper
{
  public static String getProjectPath()
  {
    URL url = UtilsHelper.class.getProtectionDomain().getCodeSource().getLocation();
    String filePath = null;
    try {
      filePath = URLDecoder.decode(url.getPath(), "utf-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (filePath.endsWith(".jar")){
      filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
    }
    //去掉bin/，不去掉则路径在编译的文件夹中
    filePath = filePath.indexOf("bin")==-1?filePath:filePath.substring(0,filePath.indexOf("bin"));
    File file = new File(filePath);
    filePath = file.getAbsolutePath();
    return filePath;
  }

  public static boolean reNameFile(String sourceName)
  {
    File oldFile = new File(sourceName);
    if (!oldFile.exists()) {
      return false;
    }
    return oldFile.delete();
  }
  
  public static boolean reNameFile(String sourceName, String nowName)
  {
    File oldFile = new File(sourceName);
    if (!oldFile.exists()) {
      return false;
    }
    File newFile = new File(nowName);
    if (newFile.exists()) {
      newFile.delete();
    }
    return oldFile.renameTo(newFile);
  }
}