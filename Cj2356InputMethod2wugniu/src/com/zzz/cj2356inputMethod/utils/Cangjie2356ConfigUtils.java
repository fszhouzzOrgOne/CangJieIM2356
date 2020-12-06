package com.zzz.cj2356inputMethod.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.zzz.cj2356inputMethod.mb.IOUtils;

import android.content.Context;

/**
 * 配置文件操作工具
 * 
 */
public class Cangjie2356ConfigUtils {

    private static boolean inited = false;

    private static Context context;

    /**
     * 配置文件名
     */
    private static String configName = "cangjie2356.config";
    private static String configFullName = "config" + File.separator + configName;
    /**
     * 外部配置文件名
     */
    private static String configOuterName = null;

    /**
     * 所有配置項
     */
    private static Map<String, String> configMap = null;

    public static void init(Context con) {
        if (inited) {
            return;
        }
        inited = true;
        context = con;
        configOuterName = context.getFilesDir().toString() + File.separator + configName;

        // 配置文件複製出去，只操作外部配置文件
        // 原始文件用於重置配置
        try {
            boolean shouldCopy = true;
            File destFile = new File(configOuterName);
            if (destFile.exists()) {
                // 是一样的文件
                if (IOUtils.isSameFile(context.getResources().getAssets().open(configFullName),
                        new FileInputStream(destFile))) {
                    // TODO shouldCopy = false;
                }
                if (shouldCopy) {
                    destFile.delete();
                }
            }
            if (shouldCopy) {
                IOUtils.copyFile(context.getResources().getAssets().open(configFullName),
                        new FileOutputStream(destFile));
            }
        } catch (Exception e) {
        }

        // 讀配置：先讀外部，出現錯誤再讀原始
        try {
            File destFile = new File(configOuterName);
            readConfigs(new FileInputStream(destFile));
        } catch (Exception e) {
            try {
                readConfigs(context.getResources().getAssets().open(configFullName));
            } catch (Exception e1) {
            }
        }
    }

    public static String getConfig(String key) {
        if (null == configMap) {
            return null;
        }
        return configMap.get(key);
    }

    /**
     * 讀取配置文件
     * 
     * @param in
     *            配置文件輸入流
     */
    public static void readConfigs(InputStream in) {
        configMap = new HashMap<String, String>();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(in, "UTF-8");
            br = new BufferedReader(isr);
            String str = null;
            while ((str = br.readLine()) != null) {
                if (!"".equals(str)) {
                    String line = str.trim(); // 去首尾
                    if (!line.startsWith("#") && line.indexOf("=") != -1) {
                        String[] keyval = line.split("=");
                        if (null != keyval && keyval.length == 2) {
                            String key = keyval[0];
                            String val = keyval[1];
                            configMap.put(key, val);
                        }
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                br.close();
            } catch (IOException e) {
            }
        }
    }
}
