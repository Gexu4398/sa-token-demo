package com.gregory.satokendemo.ssoservice.controller;

import cn.dev33.satoken.sso.template.SaSsoServerUtil;
import cn.dev33.satoken.sso.util.SaSsoConsts;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class H5Controller {

  @RequestMapping("/sso/getRedirectUrl")
  public SaResult getRedirectUrl(String client, String redirect, String mode) {

    // 未登录情况下，返回 code=401
    if (!StpUtil.isLogin()) {
      return SaResult.code(401);
    }
    // 已登录情况下，构建 redirectUrl
    redirect = SaFoxUtil.decoderUrl(redirect);
    if (SaSsoConsts.MODE_SIMPLE.equals(mode)) {
      // 模式一
      SaSsoServerUtil.checkRedirectUrl(client, redirect);
      return SaResult.data(redirect);
    } else {
      // 模式二或模式三
      String redirectUrl = SaSsoServerUtil.buildRedirectUrl(client, redirect, StpUtil.getLoginId(),
          StpUtil.getTokenValue());
      return SaResult.data(redirectUrl);
    }
  }
}
