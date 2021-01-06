package kr.co.pcninc.bigdata.cooperationmodule.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kr.co.pcninc.bigdata.cooperationmodule.common.Constants;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static String today() {
        return today(Constants.DEFAULT_DATE_FORMAT);
    }

    public static String now() {
        return now(Constants.DEFAULT_DATETIME_FORMAT);
    }

    /**
     * @fn today
     * @remark
     * - 현재 날짜(시간) 리턴
     * @param format
     * @return
     */
    public static String today(final String format) {
        final Date date = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String now(final String format) {
        final Date date = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     *
     * @fn format
     * @remark
     * - 날짜포맷 변경
     * @param dt
     * @param format
     * @return
     * @throws ParseException
     */
    public static String format(final Date dt, final String format) throws ParseException {

        if (ObjectUtils.isNull(dt)) {
            return null;
        }

        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dt);
    }

}
