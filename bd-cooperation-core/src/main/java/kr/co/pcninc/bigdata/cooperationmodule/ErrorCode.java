package kr.co.pcninc.bigdata.cooperationmodule;

public enum ErrorCode {
    UN_AUTHORIZATION("401", "인증키가 없습니다."),
    TOKEN_EXPIRED("401-001", "인증키가 만료 되었습니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String message() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
