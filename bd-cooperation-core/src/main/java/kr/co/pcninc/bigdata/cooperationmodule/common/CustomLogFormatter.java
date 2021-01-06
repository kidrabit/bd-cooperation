package kr.co.pcninc.bigdata.cooperationmodule.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CustomLogFormatter extends Formatter {

    private static final String BRACKET_LEFT = "[";
    private static final String BRACKET_RIGHT = "]";
    private static final String WHITESPACE = " ";

    @Override
    public String format(LogRecord record) {
        StringBuffer buf = new StringBuffer(1000);
        buf.append(record.getLevel());
        buf.append(WHITESPACE);
        buf.append(LocalDateTime.now());
        buf.append(WHITESPACE);
        buf.append(BRACKET_LEFT + record.getSourceClassName() + BRACKET_RIGHT);
        buf.append(record.getMessage());
        buf.append("\n");
        return buf.toString();
    }

    public String getTail(Handler h){
        return "\n";
    }
}
