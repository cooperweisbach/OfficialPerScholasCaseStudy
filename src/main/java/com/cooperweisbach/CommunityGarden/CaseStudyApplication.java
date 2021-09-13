package com.cooperweisbach.CommunityGarden;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@Slf4j
public class CaseStudyApplication extends SpringBootServletInitializer {

	static String IMAGE_DIR;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CaseStudyApplication.class);
	}


	public static void main(String[] args) throws IOException {
		IMAGE_DIR = new File(".").getCanonicalPath() + "/images/";
		log.warn(IMAGE_DIR);
		SpringApplication.run(CaseStudyApplication.class, args);
	}

}
