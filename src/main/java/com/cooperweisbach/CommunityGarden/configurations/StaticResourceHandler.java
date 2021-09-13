package com.cooperweisbach.CommunityGarden.configurations;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Configuration
@Slf4j
public class StaticResourceHandler implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        try {
            exposeDirectory("src/main/resources/static/images/", registry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exposeDirectory(String directory, ResourceHandlerRegistry registry) throws IOException {
        Path uploadDir = Paths.get(directory);
        String uploadPath = uploadDir.toFile().getCanonicalPath();
        log.warn(uploadPath);
        log.warn(directory);
        if (directory.startsWith("../")) directory = directory.replace("../", "");

        registry.addResourceHandler("/" + "images" + "/**").addResourceLocations("file:/"+ uploadPath + "/");
    }
}
