package com.bluewater.lib;

import java.util.Calendar;

/**
 * CalendarUtils（日历类）
 */
public class CalendarUtils
{
    /**
     * 判断是否是闰年
     * @param iYear
     */
    public static boolean isLeapYear(int iYear)
    {
        if (iYear % 100 == 0 && iYear % 400 == 0)
        {
            return true;
        }
        else if (iYear % 100 != 0 && iYear % 4 == 0)
        {
            return true;
        }

        return false;
    }

    /**
     * 得到某月多少天
     * 1357810腊三十一天永不差,469冬三十日平年二月28,闰年再把一天加
     * @param isLeapYear    是否闰年
     * @param iMonth         月份
     * @return              返回天数
     */
    public static int getDaysOfMonth(boolean isLeapYear, int iMonth)
    {
        int days = 0;
        switch (iMonth)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                if (isLeapYear)
                {
                    days = 29;
                }
                else
                {
                    days = 28;
                }
        }
        return days;
    }

    /**
     * 得到某年某月的一号是星期几
     * @param iYear     年
     * @param iMonth    月
     * @return          返回值：0代表周日，1-6代表周一到周六
     */
    public static int getWeekdayOfMonth(int iYear, int iMonth)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(iYear, iMonth - 1, 1);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }
}
