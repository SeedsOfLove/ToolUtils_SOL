package com.bluewater.toolutilslib;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 常用工具类
 */
public class NormalUtils
{
    /**
     * dp转px
     * @param resources
     * @param dp
     * @return
     */
    public static float dp2px(Resources resources, float dp)
    {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    /**
     * sp转px
     * @param resources
     * @param sp
     * @return
     */
    public static float sp2px(Resources resources, float sp)
    {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     * 保存文本到本地路径
     * @param content
     * @param path          路径
     * @param fileName      文件名
     * @return
     */
    public static File saveText(String content, String path, String fileName)
    {
        File appDir = new File(path);
        if (!appDir.exists())       //如果不存在，那就建立这个文件夹
        {
            appDir.mkdir();
        }

        File file = new File(appDir, fileName);

        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return file;
    }

    /**
     * 加载本地文本文件数据
     * @param path          路径
     * @param fileName      文件名
     * @return  返回文本信息
     */
    public static String loadText(String path, String fileName)
    {
        String result;

        File file = new File(path + fileName);

        try
        {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
                sb.append("\n");       //保留原文件的换行
            }

            br.close();

            result = sb.toString();

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            result = "读取文本信息失败";
        } catch (IOException e)
        {
            e.printStackTrace();
            result = "读取文本信息失败";
        }

        return result;
    }

    /**
     * 保存图片到本地路径
     * @param bmp           Bitmap
     * @param path          路径
     * @param fileName      文件名
     * @return
     */
    public static File saveImage(Bitmap bmp, String path, String fileName)
    {
        File appDir = new File(path);
        if (!appDir.exists())       //如果不存在，那就建立这个文件夹
        {
            appDir.mkdir();
        }

        File file = new File(appDir, fileName);
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 判断文件是否是图片文件
     * @param filePath
     * @return
     */
    public static boolean isImageFile(String filePath)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        if (options.outWidth == -1)
        {
            return false;
        }
        return true;
    }




}

