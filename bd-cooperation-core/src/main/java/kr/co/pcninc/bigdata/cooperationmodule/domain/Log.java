package kr.co.pcninc.bigdata.cooperationmodule.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Data
public class Log {
    String logLevel;
    LocalDateTime time;
    String className;
    String logMessage;

    public Log(String logLevel, LocalDateTime time, String className, String logMessage) {
        this.logLevel = logLevel;
        this.time = LocalDateTime.now();
        this.className = className;
        this.logMessage = logMessage;
    }
}
