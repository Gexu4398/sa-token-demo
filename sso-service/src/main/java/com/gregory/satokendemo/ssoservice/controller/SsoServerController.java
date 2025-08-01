package com.gregory.satokendemo.ssoservice.controller;

import cn.dev33.satoken.sso.processor.SaSsoServerProcessor;
import cn.dev33.satoken.sso.template.SaSsoServerTemplate;
import cn.dev33.satoken.util.SaResult;
import com.gregory.satokendemo.bizmodel.repository.UserEntityRepository;
import com.gregory.satokendemo.ssoservice.service.SsoServerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Tag(name = "鉴权管理")
@RestController
@RequestMapping
@Validated
public class SsoServerController {

  private final SsoServerService ssoServerService;

  private final UserEntityRepository userEntityRepository;

  @Autowired
  public SsoServerController(SsoServerService ssoServerService,
      UserEntityRepository userEntityRepository) {

    this.ssoServerService = ssoServerService;
    this.userEntityRepository = userEntityRepository;
  }

  // SSO-Server端：处理所有SSO相关请求
  // http://{host}:{port}/sso/auth           -- 单点登录授权地址
  // http://{host}:{port}/sso/doLogin        -- 账号密码登录接口，接受参数：name、pwd
  // http://{host}:{port}/sso/signout        -- 单点注销地址（isSlo=true时打开）
  @RequestMapping("/sso/*")
  public Object ssoRequest() {

    return SaSsoServerProcessor.instance.dister();
  }

  @Autowired
  private void configSso(@Nonnull SaSsoServerTemplate ssoServerTemplate) {

    ssoServerTemplate.strategy.notLoginView = () -> new ModelAndView("sa-login.html");

    ssoServerTemplate.strategy.doLoginHandle = (name, pwd) -> {
      final var saTokenInfo = ssoServerService.doLogin(name, pwd);
      return SaResult.ok("登录成功").setData(saTokenInfo);
    };

    // 添加消息处理器：userinfo (获取用户资料) （用于为 client 端开放拉取数据的接口）
    ssoServerTemplate.messageHolder.addHandle("userinfo", (ssoTemplate, message) -> {
      final var loginId = message.get("loginId");
      final var userId = String.valueOf(loginId);

      final var userEntity = userEntityRepository.findById(userId)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在"));

      return SaResult.ok()
          .set("id", userEntity.getId())
          .set("username", userEntity.getUsername())
          .set("name", userEntity.getFirstName());
    });
  }
}
