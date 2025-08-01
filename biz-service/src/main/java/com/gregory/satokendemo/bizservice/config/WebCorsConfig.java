package com.gregory.satokendemo.bizservice.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import jakarta.annotation.Nonnull;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
    configuration.setAllowedMethods(List.of(CorsConfiguration.ALL));
    // 此处如果设置 allowCredentials 为 true，那么就要 allowOriginPatterns，否则 500。
    configuration.setAllowCredentials(true);
    configuration.setAllowedOriginPatterns(List.of(CorsConfiguration.ALL));
    // 告知浏览器此相应头可以暴露给 js，因为部分接口需要用到。
    configuration.addExposedHeader("x-amz-meta-username");
    configuration.addExposedHeader("x-amz-meta-status");
    configuration.addExposedHeader("x-amz-meta-retries");
    configuration.addExposedHeader("x-amz-meta-origin-filename");
    configuration.setMaxAge(5000L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // 此处要使用 /** 而不能用 * 或者 /*，否则 CorsFilter 会匹配不到路径，导致 CORS 错误。
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public SaServletFilter getSaServletFilter() {

    return new SaServletFilter()
        .addInclude("/**")
        .addExclude("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/favicon.ico")
        .setBeforeAuth(obj ->
            SaRouter.match(SaHttpMethod.OPTIONS).free(r ->
                SaHolder.getResponse().setStatus(HttpStatus.NO_CONTENT.value())));
  }

  @Override
  public void addInterceptors(@Nonnull InterceptorRegistry registry) {

    registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
  }
}
