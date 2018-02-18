package com.gavronek.toyrobot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;

@SpringBootApplication
public class ToyrobotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToyrobotApplication.class, args);
	}

	@Bean
	@Primary
	public ObjectMapper jacksonObjectMapper() {
		return new ObjectMapper()
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
				.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
				.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true)
				.findAndRegisterModules();
	}
}
