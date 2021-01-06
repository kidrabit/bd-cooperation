package kr.co.pcninc.bigdata.cooperationmodule.api.exception;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @class HttpException
 * 	kr.co.pcninc.bigdata.cooperationmodule.api.exception
 * @section 클래스작성정보
 *    |    항  목        	|       	내  용       			|
 *    | :--------: 	| -----------------------------	|
 *    | Company 	| PCN
 *    | Author 		| wspark
 *    | Date 		| 2019. 3. 27.
 *    | 작업자 		| wspark, Others...
 * @section 상세설명
 * - Http 요청에 대한 Exception
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HttpException extends ServletException {

    private static final long serialVersionUID = 1L;
    private HttpStatus httpStatus;
    private String message;

    public HttpException(HttpStatus httpStatus) {
        super();
        this.httpStatus = httpStatus;
        this.message = httpStatus.getReasonPhrase();
    }

    public HttpException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public HttpException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
