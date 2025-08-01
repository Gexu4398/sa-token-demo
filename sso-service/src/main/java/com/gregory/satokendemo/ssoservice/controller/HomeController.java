package com.gregory.satokendemo.ssoservice.controller;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  // 平台化首页
  @RequestMapping({"/", "/home"})
  public Object index() {

    // 如果未登录，则先去登录
    if (!StpUtil.isLogin()) {
      return SaHolder.getResponse().redirect("/sso/auth");
    }

    // 拼接各个子系统的地址，格式形如：/sso/auth?client=xxx&redirect=${子系统首页}/sso/login?back=${子系统首页}
    String link = "/sso/auth?client=console-cli&redirect=http://localhost:8081/sso/login?back=http://localhost:8081/";

    // 组织网页结构返回到前端
    String title = "<h2>SSO 平台首页 (平台中心模式)</h2>";
    String client = "<p><a href='" + link + "' target='_blank'> 进入console-cli系统 </a></p>";

    return title + client;
  }
}
