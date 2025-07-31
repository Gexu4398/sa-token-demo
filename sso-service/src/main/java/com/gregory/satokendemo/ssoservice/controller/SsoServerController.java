package com.gregory.satokendemo.ssoservice.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.gregory.satokendemo.ssoservice.service.SsoServerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "鉴权管理")
@RestController("sso")
@RequestMapping
@Validated
public class SsoServerController {

  private final SsoServerService ssoServerService;

  @Autowired
  public SsoServerController(SsoServerService ssoServerService) {

    this.ssoServerService = ssoServerService;
  }

  @Operation(summary = "登录")
  @PostMapping("doLogin")
  public SaTokenInfo doLogin(@RequestParam String username, @RequestParam String password) {

    return ssoServerService.doLogin(username, password);
  }

  @Operation(summary = "登出")
  @PostMapping("logout")
  @SaCheckLogin
  public void logout() {

    StpUtil.logout();
  }

  @Operation(summary = "指定账号强制注销")
  @PostMapping("logout/{id}")
  public void logout(@PathVariable String id) {

    StpUtil.logout(id);
  }

  @Operation(summary = "指定账号踢下线")
  @PostMapping("kickout/{id}")
  public void kickout(@PathVariable String id) {

    StpUtil.kickout(id);
  }
}
