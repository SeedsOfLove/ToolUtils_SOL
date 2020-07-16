package com.bluewater.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bluewater.toolutilslib.AppUtils;

public class MainActivity extends AppCompatActivity
{

    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mText = findViewById(R.id.text);

        String pName = AppUtils.getPackageName(this);
        String dir = AppUtils.getAppDownloadDir(pName);

        int versionCode = AppUtils.getVersionCode(this);
        String versionName = AppUtils.getVersionName(this);

        int widthpx = AppUtils.getScreenWidthPX(this);
        int heightpx = AppUtils.getScreenHeightPX(this);

        int widthdp = AppUtils.getScreenWidthDP(this);
        int heightdp = AppUtils.getScreenHeightDP(this);

        int statusHeight = AppUtils.getStatusHeight(this);

        boolean isTel = AppUtils.isTelephonyEnabled(this);

        mText.setText("文件下载地址：" + dir + "\n"
                + "版本号：" + versionCode + "\n"
                + "版本名称：" + versionName + "\n"
                + "屏幕宽度px：" + widthpx + "\n"
                + "屏幕高度px：" + heightpx + "\n"
                + "屏幕宽度dp：" + widthdp + "\n"
                + "屏幕高度dp：" + heightdp + "\n"
                + "状态栏高度：" + statusHeight + "\n"
                + "是否支持电话：" + isTel + "\n"
        );
    }
}