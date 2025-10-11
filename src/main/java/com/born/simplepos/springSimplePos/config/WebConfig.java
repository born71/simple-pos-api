package com.born.simplepos.springSimplePos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map URL path /uploads/** to the physical folder ./uploads/
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/"); // "file:" prefix means local directory
    }
}