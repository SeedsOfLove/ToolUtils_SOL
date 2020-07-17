package com.bluewater.toolutilslib;

import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.content.ContentValues.TAG;


/**
 * 网络工具类
 */
public class NetworkUtils
{
    /** 网络不可用 */
    public static final int NETWORK_NO = 0;
    /** 移动网络4G网 */
    public static final int NETWORK_4G = 1;
    /** 移动网络3G网 */
    public static final int NETWORK_3G = 2;
    /** 移动网络2G网 */
    public static final int NETWORK_2G = 3;
    /** wifi连接 */
    public static final int NETWORK_WIFI = 4;
    /** 未知网络 */
    public static final int NETWORK_UNKNOWN = 5;


    /**
     * 判断网络是否连接
     * @param context
     * @return
     */
    public static boolean isNetWorkConnect(Context context)
    {
        boolean isConn = false ;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            isConn = true;
        }
        return isConn;
    }

    /**
     * 获取当前网络类型
     *
     * @return 网络类型
     */
    public static int getNetworkType(Context context)
    {
        int netType = NetworkUtils.NETWORK_NO;

        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null && mNetworkInfo.isAvailable())
        {
            if (mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI)
            {
                netType = NetworkUtils.NETWORK_WIFI;
            }
            else if (mNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                switch (mNetworkInfo.getSubtype())
                {
                    case TelephonyManager.NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        netType = NetworkUtils.NETWORK_2G;
                        break;

                    case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        netType = NetworkUtils.NETWORK_3G;
                        break;

                    case TelephonyManager.NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        netType = NetworkUtils.NETWORK_4G;
                        break;

                    default:

                        String subtypeName = mNetworkInfo.getSubtypeName();
                        //  中国移动 联通 电信 三种 3G 制式
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000"))
                        {
                            netType = NetworkUtils.NETWORK_3G;
                        }
                        else
                        {
                            netType = NetworkUtils.NETWORK_UNKNOWN;
                        }
                        break;
                }
            }
            else
            {
                netType = NetworkUtils.NETWORK_UNKNOWN;
            }
        }
        return netType;
    }

    /**
     * 判断移动网络是否打开
     * @param context
     * @return
     * @throws Exception
     */
    public static boolean isMobileNetEnable(Context context)
    {
        try
        {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method getMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("getDataEnabled");
            if (getMobileDataEnabledMethod != null)
            {
                return (boolean) getMobileDataEnabledMethod.invoke(tm);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断WIFI开关是否打开
     * @param context
     * @return
     * @throws Exception
     */
    public static boolean isWiFiEnable(Context context)
    {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    /**
     * 打开或关闭 WiFi
     * @param context
     * @param enabled true:打开   false:关闭
     */
    public static void setWifiEnabled(Context context, boolean enabled)
    {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (enabled)
        {
            if (!wifiManager.isWifiEnabled())
            {
                wifiManager.setWifiEnabled(true);

                Toast.makeText(context, "wifi已打开", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "wifi已打开");
            }
        }
        else
        {
            if (wifiManager.isWifiEnabled())
            {
                wifiManager.setWifiEnabled(false);

                Toast.makeText(context, "wifi已关闭", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "wifi已关闭");
            }
        }
    }

    /**
     * 获取 IP 地址
     * @param useIPv4  true，返回IPv4   false，返回IPv6
     */
    public static String getIPAddress(boolean useIPv4)
    {
        try
        {
            for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements(); )
            {
                NetworkInterface ni = nis.nextElement();
                // 防止小米手机返回 10.0.2.15
                if (!ni.isUp()) continue;
                for (Enumeration<InetAddress> addresses = ni.getInetAddresses(); addresses.hasMoreElements(); )
                {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress())
                    {
                        String hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (useIPv4)
                        {
                            if (isIPv4) return hostAddress;
                        }
                        else
                        {
                            if (!isIPv4)
                            {
                                int index = hostAddress.indexOf('%');
                                return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (SocketException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过 wifi 获取本地 IP 地址
     * 调用此方法，会默认打开wifi开关
     * @param context
     * @return IP 地址
     */
    public static String getIpAddressByWifi(Context context)
    {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled())
        {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return intToIp(ipAddress);
    }

    /**
     * 判断是否ping通某个ip地址
     * @param ipAddress
     * @return
     */
    public static boolean pingIpAddress(String ipAddress)
    {
        String result = null;

        try
        {
            //参数"-c 1"是指ping的次数为1次，"-w 2"是指超时时间单位为2s
            Process process = Runtime.getRuntime().exec("/system/bin/ping -c 1 -w 2 " + ipAddress);

            // 读取ping的内容，可不加。
            InputStream input = process.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content;
            while ((content = in.readLine()) != null)
            {
                stringBuffer.append(content);
            }
            Log.i("TTT", "result content : " + stringBuffer.toString());

            // PING的状态
            int status = process.waitFor();
            if (status == 0)
            {
                result = "successful!";
                return true;
            }
            else
            {
                result = "failed! cannot reach the IP address";
                return false;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }finally
        {
            Log.i("TTT", "result = " + result);
        }

        return false;
    }

    /**
     * int转IP地址格式
     * @param i
     * @return
     */
    public static String intToIp(int i)
    {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

}
