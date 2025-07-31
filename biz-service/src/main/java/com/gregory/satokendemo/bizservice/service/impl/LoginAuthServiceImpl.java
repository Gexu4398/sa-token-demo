package com.gregory.satokendemo.bizservice.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.gregory.satokendemo.bizmodel.repository.UserEntityRepository;
import com.gregory.satokendemo.bizservice.service.LoginAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LoginAuthServiceImpl implements LoginAuthService {

  private final Argon2PasswordEncoder argon2PasswordEncoder;

  private final UserEntityRepository userEntityRepository;

  @Autowired
  public LoginAuthServiceImpl(Argon2PasswordEncoder argon2PasswordEncoder,
      UserEntityRepository userEntityRepository) {

    this.argon2PasswordEncoder = argon2PasswordEncoder;
    this.userEntityRepository = userEntityRepository;
  }

  @Override
  public String doLogin(String username, String password) {

    final var user = userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "账号不存在"));

    if (!argon2PasswordEncoder.matches(password, user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "账号或密码错误");
    }

    StpUtil.login(user.getId());

    return StpUtil.getTokenValueByLoginId(user.getId());
  }
}
