package com.gregory.satokendemo.bizservice.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.gregory.satokendemo.bizmodel.repository.SysUserRepository;
import com.gregory.satokendemo.bizservice.model.LoginRequest;
import com.gregory.satokendemo.bizservice.util.Argon2CustomUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "鉴权管理")
@RestController
@RequestMapping("acc")
@Validated
public class LoginAuthController {

  private final SysUserRepository sysUserRepository;

  @Autowired
  public LoginAuthController(SysUserRepository sysUserRepository) {

    this.sysUserRepository = sysUserRepository;
  }

  @Operation(summary = "登录")
  @PostMapping("doLogin")
  public void doLogin(@Valid @RequestBody LoginRequest loginRequest) {

    final var password = loginRequest.getPassword();

    final var hash = Argon2CustomUtil.hash(password);

    final var user = sysUserRepository
        .findByUsernameAndPassword(loginRequest.getUsername(), hash)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "账号或密码错误"));

    StpUtil.login(user.getId());
  }

  @Operation(summary = "是否登录")
  @PostMapping("isLogin")
  public boolean isLogin() {

    return StpUtil.isLogin();
  }

  @Operation(summary = "获取token信息")
  @GetMapping("tokenInfo")
  public SaTokenInfo tokenInfo() {

    return StpUtil.getTokenInfo();
  }

  @Operation(summary = "登出")
  @PostMapping("logout")
  public void logout() {

    StpUtil.logout();
  }

  @Operation(summary = "获取登录用户ID")
  @GetMapping("loginId")
  public String getLoginId() {

    return StpUtil.getLoginIdAsString();
  }
}
