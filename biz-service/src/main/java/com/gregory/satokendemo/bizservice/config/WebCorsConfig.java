package com.gregory.satokendemo.bizservice.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaResponse;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONObject;
import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

  @Bean
  public SaServletFilter getSaServletFilter() {

    return new SaServletFilter()
        .addInclude("/**")
        .addExclude(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/favicon.ico",
            "/login/doLogin",
            "/register/**")
        .setBeforeAuth(obj -> {
          SaHolder.getResponse()
              .setHeader("Access-Control-Allow-Origin", "*")
              .setHeader("Access-Control-Allow-Methods", "*")
              .setHeader("Access-Control-Allow-Headers", "*")
              .setHeader("Access-Control-Max-Age", "3600");
          SaRouter.match(SaHttpMethod.OPTIONS).back();
        })
        .setAuth(obj -> {
          if (!StpUtil.isLogin()) {
            SaResponse response = SaHolder.getResponse();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            SaRouter.back(new JSONObject(HttpStatus.UNAUTHORIZED.value()));
          }
        });
  }

  @Override
  public void addInterceptors(@Nonnull InterceptorRegistry registry) {

    registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
  }
}
