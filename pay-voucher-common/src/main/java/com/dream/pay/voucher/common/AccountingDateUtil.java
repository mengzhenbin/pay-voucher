package com.dream.pay.voucher.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 会计日期处理工具
 *
 * @author mengzhenbin
 * @since 2018/1/11
 */
public class AccountingDateUtil {
    /**
     * 获取当前会计日的下一个会计日
     *
     * @param now
     * @return
     */
    public static String getNextDay(String now) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(now);
        } catch (ParseException e) {
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        return sdf.format(calendar.getTime());
    }

}
