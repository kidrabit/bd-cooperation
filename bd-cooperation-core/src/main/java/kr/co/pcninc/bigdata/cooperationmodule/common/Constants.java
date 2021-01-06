package kr.co.pcninc.bigdata.cooperationmodule.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class Constants {

    public static final int ZERO = 0;
    public static final Object NULL = null;
    public static final String EMPTY = StringUtils.EMPTY;

    public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyyMMddHHmmss";
    public static final String DEFAULT_DATE_PREETY_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_PREETY_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_TIMEZONE = "Asia/Seoul";
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final String DEFAULT_CHARSET_VALUE = DEFAULT_CHARSET.displayName();
    public static final Locale DEFAULT_LOCALE = Locale.KOREAN;
    public static final String DEFAULT_LOCALE_VALUE = Locale.KOREAN.getDisplayName();

    public static final String COMMA = ",";
    public static final String Y = "Y";
    public static final String N = "N";
    public static final String FAILURE = "failure";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    public static final String ID = "id";
    public static final String ROLES = "roles";

    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
