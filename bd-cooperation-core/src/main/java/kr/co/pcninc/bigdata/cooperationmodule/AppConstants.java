package kr.co.pcninc.bigdata.cooperationmodule;

public class AppConstants {

    public static final String FAILURE_CODE_ATTRIBUTE_NAME = "response.failure.code";

    public static final String ERROR = "ERROR";
    public static final String INFO = "INFO";

    public static final int COLLECT = 0;
    public static final int PREPROCESSING = 1;
    public static final int META_PRO = 2;
    public static final int VISUALIZATION_PRO = 3;

    public static String getWorkName(int workId) {
        if(workId == COLLECT)
            return "수집";
        else if(workId == PREPROCESSING)
            return "전처리";
        else if(workId == META_PRO)
            return "META 프로파일링";
        else
            return "시각화 프로파일링";
    }
}
