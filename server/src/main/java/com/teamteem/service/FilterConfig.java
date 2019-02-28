package com.teamteem.service;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean Filter() {
    FilterRegistrationBean filter = new FilterRegistrationBean();
    filter.setFilter(new AuthFilter());
    filter.addUrlPatterns("/logged_in/*");
    return filter;
  }
}