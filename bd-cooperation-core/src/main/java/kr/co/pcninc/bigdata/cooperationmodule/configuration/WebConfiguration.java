package kr.co.pcninc.bigdata.cooperationmodule.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

import kr.co.pcninc.bigdata.cooperationmodule.ApiConstants;
import kr.co.pcninc.bigdata.cooperationmodule.configuration.interceptor.RequestTraceInterceptor;

@Slf4j
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(requestTraceInterceptor()).addPathPatterns(ApiConstants.API_URI_PREFIX + "/**");
        log.info("RequestTraceInterceptor 인터셉터가 등록 되었습니다");
    }

    @Bean
    public RequestTraceInterceptor requestTraceInterceptor() {
        return new RequestTraceInterceptor();
    }
}
