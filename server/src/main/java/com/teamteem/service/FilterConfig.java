package com.teamteem.service;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean loginFilter() {
    FilterRegistrationBean registration =
        new FilterRegistrationBean();
    registration.setFilter(new LoginFilter());
    registration.addUrlPatterns("/secured/*");
    return registration;
  }
}