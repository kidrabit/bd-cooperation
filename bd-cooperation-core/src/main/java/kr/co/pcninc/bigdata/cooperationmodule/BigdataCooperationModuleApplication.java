package kr.co.pcninc.bigdata.cooperationmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*@EnableJpaAuditing
@SpringBootApplication
public class BigdataCooperationModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigdataCooperationModuleApplication.class, args);
    }

}*/

@EnableJpaAuditing
@SpringBootApplication
public class BigdataCooperationModuleApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BigdataCooperationModuleApplication.class);
    }

    public static void main(String[] args) { SpringApplication.run(BigdataCooperationModuleApplication.class, args); }

}