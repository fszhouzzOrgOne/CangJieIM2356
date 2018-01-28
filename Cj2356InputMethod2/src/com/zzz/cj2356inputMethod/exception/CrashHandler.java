package com.zzz.cj2356inputMethod.exception;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.inputmethodservice.InputMethodService;
import android.os.Looper;
import android.widget.Toast;

/**
 * 程序崩潰的處理
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2018年1月28日 上午9:47:29
 */
public class CrashHandler implements UncaughtExceptionHandler {
    private static CrashHandler INSTANCE = new CrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable ex) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                String msg = "程序崩潰了，請重啓程序...\n" + "崩潰原因如：" + ex.getMessage();

                if (mContext instanceof InputMethodService) {
                    Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                } else {
                    new AlertDialog.Builder(mContext).setTitle("錯誤").setCancelable(false).setMessage(msg)
                            .setNeutralButton("我知道了", new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    System.exit(0);
                                }
                            }).create().show();
                }

                Looper.loop();
            }
        }.start();
    }

}
