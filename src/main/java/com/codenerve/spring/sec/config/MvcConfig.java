/*
 * Copyright (c) 2018 codenerve.com
 *
 * You may study, use, and modify this example. Redistribution is not permitted.
 */

package com.codenerve.spring.sec.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/login").setViewName("login");
    }
}