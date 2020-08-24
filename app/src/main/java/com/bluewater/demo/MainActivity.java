package com.bluewater.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bluewater.toolutilslib.AppUtils;
import com.bluewater.toolutilslib.Base64Utils;
import com.bluewater.toolutilslib.MobileInfoUtils;
import com.bluewater.toolutilslib.NetworkUtils;
import com.bluewater.toolutilslib.SharedPreferencesUtils;

public class MainActivity extends AppCompatActivity
{
    private Context mContext;
    private Activity mActivity;

    private TextView mText1;
    private TextView mText2;
    private TextView mText3;
    private TextView mText4;
    private EditText mEtSp;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mActivity = this;

        mText1 = findViewById(R.id.text1);
        mText2 = findViewById(R.id.text2);
        mText3 = findViewById(R.id.text3);
        mText4 = findViewById(R.id.text4);
        mEtSp = findViewById(R.id.et_sp);

        //---------------AppUtils
        String dir = AppUtils.getAppDownloadDir(this);

        int versionCode = AppUtils.getVersionCode(this);
        String versionName = AppUtils.getVersionName(this);

        int widthpx = AppUtils.getScreenWidthPX(this);
        int heightpx = AppUtils.getScreenHeightPX(this);

        int widthdp = AppUtils.getScreenWidthDP(this);
        int heightdp = AppUtils.getScreenHeightDP(this);

        int statusHeight = AppUtils.getStatusHeight(this);

        boolean isTel = AppUtils.isTelephonyEnabled(this);

        mText1.setText("文件下载地址：" + dir + "\n"
                + "版本号：" + versionCode + "\n"
                + "版本名称：" + versionName + "\n"
                + "屏幕宽度px：" + widthpx + "\n"
                + "屏幕高度px：" + heightpx + "\n"
                + "屏幕宽度dp：" + widthdp + "\n"
                + "屏幕高度dp：" + heightdp + "\n"
                + "状态栏高度：" + statusHeight + "\n"
                + "是否支持电话：" + isTel + "\n"
        );

        //------------------NetworkUtils
        boolean isNetConnect = NetworkUtils.isNetWorkConnect(this);
        int netType = NetworkUtils.getNetworkType(this);
        boolean isMobileDataEnable = NetworkUtils.isMobileNetEnable(this);
        boolean isWifiDataEnable = NetworkUtils.isWiFiEnable(this);
//        boolean isPingBaiDu = NetworkUtils.pingIpAddress("www.baidu.com");
        String ip = NetworkUtils.getIPAddress(true);
        String wifiIp = NetworkUtils.getIpAddressByWifi(mContext);

        mText2.setText("网络是否链接：" + isNetConnect + "\n"
                + "网络类型：" + netType + "\n"
                + "移动网络是否打开：" + isMobileDataEnable + "\n"
                + "WiFi开关是否打开：" + isWifiDataEnable + "\n"
//                + "是否ping通百度：" + isPingBaiDu + "\n"
                + "IP地址：" + ip + "\n"
                + "WiFi IP地址：" + wifiIp + "\n"
        );

        //------------------MobileInfoUtils
        String brand = MobileInfoUtils.getDeviceBrand();
        String phoneModel = MobileInfoUtils.getPhoneModel();
        String androidSystemVersion = MobileInfoUtils.getAndroidSystemVersion();
        String systemLanguage = MobileInfoUtils.getSystemLanguage();

        mText3.setText("手机厂商：" + brand + "\n"
                        + "手机型号：" + phoneModel + "\n"
                        + "系统版本号：" + androidSystemVersion + "\n"
                        + "当前手机系统语言：" + systemLanguage + "\n"
        );

        //------------------Base64Utils
        String password = "i love jing+-*/=！@#￥%……&*（）｛｝【】【】[]";

        String pwEnc = Base64Utils.encryptBASE64(password);
        String pwDec = Base64Utils.decryptBASE64(pwEnc);

        mText4.setText("原密码：" + password + "\n"
                + "Base64加密：" + pwEnc + "\n"
                + "Base64解密：" + pwDec + "\n"
        );

        //------------------SharedPreferencesUtils
        SharedPreferencesUtils spu = new SharedPreferencesUtils(mContext, "file_shared_preferences_test");
        mEtSp.setText(spu.getString("value"));
    }


    //打开或关闭wifi
    public void onClickBtn1(View view)
    {
        if (NetworkUtils.isWiFiEnable(mContext))
        {
            NetworkUtils.setWifiEnabled(mContext, false);
        }
        else
        {
            NetworkUtils.setWifiEnabled(mContext, true);
        }
    }

    //数据存储
    public void onClick2(View view)
    {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils spu = new SharedPreferencesUtils(mContext, "file_shared_preferences_test");

        spu.putValues(new SharedPreferencesUtils.ContentValue("value", mEtSp.getText().toString()));    //插入值

        Toast.makeText(mContext, spu.getString("value"), Toast.LENGTH_SHORT).show();    //获取值

    }


























}