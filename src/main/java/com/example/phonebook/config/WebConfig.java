package com.example.phonebook.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");

        File uploadDir = new File(uploadPath);
        String filesystem = Paths.get(uploadPath).getFileSystem().toString();
        if (filesystem.lastIndexOf("Windows") != -1) {
            registry.addResourceHandler("/Photo/**")
                    .addResourceLocations("file:///" + uploadDir.getAbsolutePath() + "/");
        } else {
            registry.addResourceHandler("/Photo/**")
                    .addResourceLocations("file://" + uploadDir.getAbsolutePath() + "/");
        }

    }
}
