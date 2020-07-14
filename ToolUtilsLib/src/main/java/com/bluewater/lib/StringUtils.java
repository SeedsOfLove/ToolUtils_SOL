package com.bluewater.lib;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 字符串处理类
 */
public class StringUtils
{
    /**
     * 获取中文名的拼音
     *
     * @param inputString   中文
     * @return  拼音
     */
    public static String getPingYin(String inputString)
    {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        char[] input = inputString.trim().toCharArray();
        String output = "";
        try
        {
            for (char curchar : input)
            {
                if (java.lang.Character.toString(curchar).matches("[\\u4E00-\\u9FA5]+"))    //是否为中文
                {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(curchar, format);
                    output += temp[0];
                }
                else
                {
                    output += java.lang.Character.toString(curchar);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e)
        {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * Url中包含特殊字符转换处理
     *
     * @param url
     * @return
     */
    public static String UrlSpecialCharacterHandle(String url)
    {
        String urlHandle = url.replace("%", "%25");
        urlHandle = urlHandle.replace("+", "%2B");
        urlHandle = urlHandle.replace("#", "%23");
        urlHandle = urlHandle.replace("&", "%26");
        urlHandle = urlHandle.replace(" ", "%20");
        urlHandle = urlHandle.replace("/", "%2F");
        urlHandle = urlHandle.replace("?", "%3F");
        urlHandle = urlHandle.replace("=", "%3D");
        urlHandle = urlHandle.replace("<", "%3C");
        urlHandle = urlHandle.replace(">", "%3E");
        return urlHandle;
    }

    /**
     * 判断字符串是否为数字(非负整数)
     * @param str
     * @return
     */
    public static boolean isNumber1(String str)
    {
        String reg = "^[0-9]+$";
        return str.matches(reg);
    }

    /**
     * 判断字符串是否为数字(包括浮点类型)
     * @param str
     * @return
     */
    public static boolean isNumber2(String str)
    {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    /**
     * 判断字符串是否为数字(包括浮点类型，负数，正数（含+号）)
     * @param str
     * @return
     */
    public static boolean isNumber3(String str)
    {
        String tmp;
        if (str.startsWith("-") || str.startsWith("+"))
        {
            tmp = str.substring(1);
        }
        else
        {
            tmp = str;
        }

        String reg = "^[0-9]+(.[0-9]+)?$";
        return tmp.matches(reg);
    }


}

