package com.gavronek.toyrobot;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
@EnableSwagger2
public class BeanConfigurations {

    @Bean
    @Primary
    public ObjectMapper jacksonObjectMapper() {
        return new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true)
                .findAndRegisterModules();
    }


    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(basePackage("com.gavronek.toyrobot"))
                .paths(regex("/robot.*"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(new ApiInfo(
                        "Toy Robot Simulator",
                        "This is the API documentation of the Toy Robot Simulator service",
                        "0.0.1-SNAPSHOT",
                        "https://github.com/gavronek/toy-robot",
                        new Contact("Adam Gavronek", "https://github.com/gavronek", "adam.gavronek@gmail.com"),
                        "Apache License Version 2.0",
                        "https://www.apache.org/licenses/LICENSE-2.0",
                        Collections.emptyList()));
    }
}
