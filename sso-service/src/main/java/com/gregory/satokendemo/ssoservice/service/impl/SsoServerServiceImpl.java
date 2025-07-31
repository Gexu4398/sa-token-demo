package com.gregory.satokendemo.ssoservice.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.gregory.satokendemo.bizmodel.model.UserEntity;
import com.gregory.satokendemo.bizmodel.repository.UserEntityRepository;
import com.gregory.satokendemo.ssoservice.service.SsoServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SsoServerServiceImpl implements SsoServerService {

  private final Argon2PasswordEncoder argon2PasswordEncoder;

  private final UserEntityRepository userEntityRepository;

  @Autowired
  public SsoServerServiceImpl(Argon2PasswordEncoder argon2PasswordEncoder,
      UserEntityRepository userEntityRepository) {

    this.argon2PasswordEncoder = argon2PasswordEncoder;
    this.userEntityRepository = userEntityRepository;
  }

  @Override
  public SaTokenInfo doLogin(String username, String password) {

    final var user = userEntityRepository
        .findByUsername(username)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "账号不存在"));

    if (!argon2PasswordEncoder.matches(password, user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "账号或密码错误");
    }

    if (!user.isEnabled()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "账号已被停用");
    }

    switch (user.getStatus()) {
      case UserEntity.STATUS_LOCKED ->
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "账号已被锁定");
      case UserEntity.STATUS_PENDING ->
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "账号正在审核中");
      case UserEntity.STATUS_DISABLED ->
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "账号已停用");
      case UserEntity.STATUS_REJECTED ->
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "账号审核未通过");
    }

    StpUtil.login(user.getId());

    return StpUtil.getTokenInfo();
  }
}
