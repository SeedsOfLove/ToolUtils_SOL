package com.bluewater.toolutilslib.FTPClient;

import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * FTP客户端工具类
 *
 * FTP客户端使用流程：
 * 1.在Android手机上创建一个FTP客户端
 * 2.设置host及端口建立与FTP服务器的连接
 * 3.使用用户名和密码登录FTP服务器
 * 4.更改目录，将当前客户端指向的服务端的文件夹从跟目录调整到指定的目录
 * 5.上传文件，并等待上传完成的结果
 * 6.断开和FTP服务器的链接
 */
public class FTPClientUtil
{
    private static final String TAG = "FTPClientUtil";

    private FTPClient ftpClient = null;     // FTP客户端

    /**
     * 连接到FTP服务器
     *
     * @param host     ftp服务器域名
     * @param username 访问用户名
     * @param password 访问密码
     * @param port     端口
     * @return 是否连接成功
     */
    public boolean ftpConnect(String host, String username, String password, int port)
    {
        try
        {
            ftpClient = new FTPClient();

            Log.i(TAG, "connecting to the ftp server " + host + ":" + port);
            ftpClient.connect(host, port);      //连接

            // 根据返回的状态码，判断链接是否建立成功
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode()))
            {
                Log.i(TAG, "login to the ftp server");
                boolean status = ftpClient.login(username, password);   //登录

                // 设置连接超时时间,10秒
                ftpClient.setConnectTimeout(10000);
                // 设置中文编码集，防止中文乱码
                ftpClient.setControlEncoding("UTF-8");

                /*
                 * 设置文件传输模式
                 * 避免一些可能会出现的问题，在这里必须要设定文件的传输格式。
                 * 在这里我们使用BINARY_FILE_TYPE来传输文本、图像和压缩文件。
                 */
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();

                return status;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            Log.e(TAG, "Error: could not connect to host " + host);
        }
        return false;
    }

    /**
     * 断开ftp服务器连接
     *
     * @return 断开结果
     */
    public boolean ftpDisconnect()
    {
        // 判断空指针
        if (ftpClient == null)
        {
            return true;
        }

        // 断开ftp服务器连接
        try
        {
            ftpClient.logout();
            ftpClient.disconnect();
            return true;
        } catch (Exception e)
        {
            Log.e(TAG, "Error occurred while disconnecting from ftp server.");
        }
        return false;
    }

    /**
     * ftp 文件上传
     *
     * @param file  源文件
     * @param desFileName  文件名称
     * @return 文件上传结果
     */
    public boolean ftpUpload(File file, String desFileName, FtpUploadProgressListener listener)
    {
        boolean status = false;
        try
        {
            FileInputStream srcFileStream = new FileInputStream(file);

            // 带有进度的方式
            BufferedInputStream buffIn = new BufferedInputStream(srcFileStream);
            ProgressInputStream progressInput = new ProgressInputStream(buffIn, listener, file);

            desFileName = new String(desFileName.getBytes("GBK"),"iso-8859-1");     //解决文件名为中文时上传失败的问题（注意：下载、获取该文件信息时，也需要反转一下文件名称编码）

            status = ftpClient.storeFile(desFileName, progressInput);

            buffIn.close();
            srcFileStream.close();

            return status;

        } catch (Exception e)
        {
            e.printStackTrace();
            Log.e(TAG, "upload failed: " + e.getLocalizedMessage());
        }
        return status;
    }

    /**
     * ftp 更改目录
     *
     * @param path 更改的路径
     * @return 更改是否成功
     */
    public boolean ftpChangeDir(String path)
    {
        boolean status = false;
        try
        {
            status = ftpClient.changeWorkingDirectory(path);
        } catch (Exception e)
        {
            e.printStackTrace();
            Log.e(TAG, "change directory failed: " + e.getLocalizedMessage());
        }
        return status;
    }

    /*
     * 上传进度监听
     */
    public interface FtpUploadProgressListener
    {
        void onUploadProgress(long uploadSize, File file);
    }

}
