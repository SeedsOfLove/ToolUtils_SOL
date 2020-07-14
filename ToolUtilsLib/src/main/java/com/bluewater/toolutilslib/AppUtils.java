package com.bluewater.toolutilslib;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.lang.reflect.Field;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * App相关工具类
 */
public class AppUtils
{
    /**
     * 获取当前本地apk的版本号(如1，2，3...)
     * 对应gradle下android:versionCode
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext)
    {
        int versionCode = 0;
        try
        {
            //获取软件版本号，
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            {
                versionCode = (int) packageInfo.getLongVersionCode();
            }
            else
            {
                versionCode = packageInfo.versionCode;
            }

        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称(如V1.11，V1.12，V1.13...)
     * 对应gradle下android:versionName
     * @param context 上下文
     * @return
     */
    public static String getVersionName(Context context)
    {
        String verName = "";
        try
        {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 获取APP的应用程序包名
     * @param context 上下文
     * @return
     */
    public static String getPackageName(Context context)
    {
        String packageName = null;
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            packageName = packageInfo.packageName;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return packageName;
    }

    /**
     * 获取App更新文件（apk）本地下载路径
     * @param apkName   apk名称
     * @return
     */
    public static String getAPKLocalDir(String apkName)
    {
        File sdDir = null;

        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
        if (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();       // 获取根目录
        }

//        String apkName = BaseApplication.getContext().getResources().getString(R.string.app_name);

        String dirpath;
        if (sdDir != null)
        {
            dirpath = sdDir.toString() + "/AppName/" + apkName + ".apk";
        }
        else
        {
            dirpath = "/AppName/" + apkName + ".apk";
        }

        return dirpath;
    }

    /**
     * 获得屏幕宽度px(像素)
     *
     * @param context
     * @return
     */
    public static int getScreenWidthPX(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        int width = outMetrics.widthPixels;         // 屏幕宽度（像素）
        return width;
    }

    /**
     * 获得屏幕高度px(像素)
     *
     * @param context
     * @return
     */
    public static int getScreenHeightPX(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        int height = outMetrics.heightPixels;       // 屏幕高度（像素）
        return height;
    }

    /**
     * 获得屏幕宽度dp
     *
     * @param context
     * @return
     */
    public static int getScreenWidthDP(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        int width = outMetrics.widthPixels;         // 屏幕宽度（像素）
        float density = outMetrics.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = outMetrics.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）

        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)

        return screenWidth;
    }

    /**
     * 获得屏幕高度dp
     *
     * @param context
     * @return
     */
    public static int getScreenHeightDP(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);


        int height = outMetrics.heightPixels;       // 屏幕高度（像素）
        float density = outMetrics.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = outMetrics.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）

        int screenHeight = (int) (height / density);// 屏幕高度(dp)

        return screenHeight;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context)
    {
        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            Field field = clazz.getField("status_bar_height");
            int height = Integer.parseInt(field.get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }


    /*
        举例：
        btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (!Utils.isFastClick())  //防止过快点击造成多次事件执行
                    {
                        ..................
                    }
                }
            });
    */
    private static final int MIN_CLICK_DELAY_TIME = 1000;   // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static long lastClickTime;
    /**
     * 防止按钮重复点击
     *
     * @return true 重复点击；  false 正常点击
     */
    public static boolean isFastClick()
    {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) <= MIN_CLICK_DELAY_TIME)
        {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * 判断设备是否支持电话功能
     * @param activity
     * @return
     */
    public static boolean isTelephonyEnabled(Activity activity)
    {
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }




}

