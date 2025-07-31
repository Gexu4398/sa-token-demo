package com.gregory.satokendemo.ssoservice.service;

import cn.dev33.satoken.stp.SaTokenInfo;

public interface SsoServerService {

  SaTokenInfo doLogin(String username, String password);
}
