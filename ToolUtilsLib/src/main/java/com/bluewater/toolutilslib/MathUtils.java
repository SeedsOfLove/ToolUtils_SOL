package com.bluewater.toolutilslib;

/**
 * 数学类
 */
public class MathUtils
{
    /**
     * 转16进制
     * @param bytes
     * @return
     */
    public static String getHex(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; --i)
        {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i > 0)
            {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * 转10进制
     * @param bytes
     * @return
     */
    public static long getDec(byte[] bytes)
    {
        long result = 0;
        long factor = 1;
        for (int i = 0; i < bytes.length; ++i)
        {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    /**
     * 逆序
     * @param bytes
     * @return
     */
    public static long getReversed(byte[] bytes)
    {
        long result = 0;
        long factor = 1;
        for (int i = bytes.length - 1; i >= 0; --i)
        {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }
}
