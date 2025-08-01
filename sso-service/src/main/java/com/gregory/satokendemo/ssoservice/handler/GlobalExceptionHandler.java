package com.gregory.satokendemo.ssoservice.handler;

import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // 全局异常拦截
  @ExceptionHandler
  public SaResult handlerException(Exception e) {

    log.error("系统异常：", e);
    return SaResult.error(e.getMessage());
  }
}
