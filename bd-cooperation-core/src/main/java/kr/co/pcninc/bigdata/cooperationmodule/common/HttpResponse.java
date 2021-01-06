package kr.co.pcninc.bigdata.cooperationmodule.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import kr.co.pcninc.bigdata.cooperationmodule.common.Constants;

/**
 * @class HttpResponse
 * 	kr.co.pcninc.solutions.mask.common.message.http
 * @section 클래스작성정보
 *    |    항  목        	|       	내  용       			|
 *    | :--------: 	| -----------------------------	|
 *    | Company 	| PCN
 *    | Author 		| fanta
 *    | Date 		| 2020. 2. 20.
 *    | 작업자 		| fanta, Others...
 * @section 상세설명
 * - http 응답 메시지
 */
@Data
@NoArgsConstructor
public class HttpResponse<T> {

    private String result = Constants.SUCCESS;

    private String failure;

    private T body;

    public HttpResponse(T body) {
        this.body = body;
    }

    public HttpResponse(T body, String result) {
        this.body = body;
        this.result = result;
    }

    public HttpResponse(T body, String result, String failure) {
        this.body = body;
        this.result = result;
        this.failure = failure;
    }
}
