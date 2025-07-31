package com.gregory.satokendemo.bizservice.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.gregory.satokendemo.bizservice.model.LoginRequest;
import com.gregory.satokendemo.bizservice.service.LoginAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户认证")
@RestController
@RequestMapping("login")
@Validated
public class LoginController {

  private final LoginAuthService loginAuthService;

  @Autowired
  public LoginController(LoginAuthService loginAuthService) {

    this.loginAuthService = loginAuthService;
  }

  @Operation(summary = "登录")
  @PostMapping("doLogin")
  public String doLogin(@Valid @RequestBody LoginRequest loginRequest) {

    return loginAuthService.doLogin(loginRequest.getUsername(), loginRequest.getPassword());
  }

  @SaCheckRole("super-admin")
  @Operation(summary = "是否登录")
  @PostMapping("isLogin")
  public boolean isLogin() {

    return StpUtil.isLogin();
  }

  @Operation(summary = "获取token信息")
  @GetMapping("tokenInfo")
  @SaCheckLogin
  public SaTokenInfo tokenInfo() {

    return StpUtil.getTokenInfo();
  }

  @Operation(summary = "登出")
  @PostMapping("logout")
  @SaCheckLogin
  public void logout() {

    StpUtil.logout();
  }

  @Operation(summary = "获取登录用户ID")
  @GetMapping("loginId")
  @SaCheckLogin
  public String getLoginId() {

    return StpUtil.getLoginIdAsString();
  }
}
