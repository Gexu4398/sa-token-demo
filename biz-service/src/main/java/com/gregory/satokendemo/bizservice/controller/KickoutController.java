package com.gregory.satokendemo.bizservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "踢出管理")
@RestController
@RequestMapping("kickout")
@Validated
public class KickoutController {

  @Operation(summary = "指定账号强制注销")
  @PostMapping("logout")
  public void logout(@RequestParam String loginId) {

    StpUtil.logout(loginId);
  }

  @Operation(summary = "指定账号踢下线")
  @PostMapping("kickout")
  public void kickout(@RequestParam String loginId) {

    StpUtil.kickout(loginId);
  }
}
