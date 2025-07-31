package com.gregory.satokendemo.bizservice.service;

import cn.dev33.satoken.stp.SaTokenInfo;

public interface LoginAuthService {

  SaTokenInfo doLogin(String username, String password);
}
