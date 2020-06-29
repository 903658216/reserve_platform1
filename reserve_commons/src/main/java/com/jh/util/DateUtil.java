package com.jh.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期date的工具类
 */
public class DateUtil {


    /**
     * 获得当天之后next天的日期
     * @param next
     * @return
     */
    public static Date getNextDate(int next){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,next);
        return calendar.getTime();

    }
}
