package com.gregory.satokendemo.bizservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gregory.satokendemo.bizservice.model.RegisterUserRequest;
import com.gregory.satokendemo.bizservice.service.UserService;
import com.gregory.satokendemo.ssomodel.model.UserEntity;
import com.gregory.satokendemo.ssomodel.repository.SysGroupRepository;
import com.gregory.satokendemo.ssomodel.repository.UserEntityRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

  private final Argon2PasswordEncoder argon2PasswordEncoder;

  private final UserEntityRepository userEntityRepository;

  private final SysGroupRepository sysGroupRepository;

  @Autowired
  public UserServiceImpl(Argon2PasswordEncoder argon2PasswordEncoder,
      UserEntityRepository userEntityRepository, SysGroupRepository sysGroupRepository) {

    this.argon2PasswordEncoder = argon2PasswordEncoder;

    this.userEntityRepository = userEntityRepository;
    this.sysGroupRepository = sysGroupRepository;
  }

  @Override
  public UserEntity getUserByName(String username) {

    return userEntityRepository.findByUsername(username)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在"));
  }

  @Override
  public UserEntity registerUser(@Nonnull RegisterUserRequest registerUserRequest) {

    if (userEntityRepository.existsByUsername(registerUserRequest.getUsername())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "账号已存在");
    }

    if (StrUtil.isNotBlank(registerUserRequest.getEmail()) &&
        userEntityRepository.existsByEmail(registerUserRequest.getEmail())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "邮箱已存在");
    }

    final var ur = new UserEntity();
    ur.setUsername(registerUserRequest.getUsername());
    ur.setFirstName(registerUserRequest.getFirstName());
    ur.setLastName(registerUserRequest.getLastName());
    ur.setEnabled(true);
    ur.setStatus(UserEntity.STATUS_PENDING);
    ur.setPicture(registerUserRequest.getPicture());
    ur.setEmail(registerUserRequest.getEmail());
    ur.setPhoneNumber(registerUserRequest.getPhoneNumber());

    final var hashedPassword = argon2PasswordEncoder.encode(registerUserRequest.getPassword());
    ur.setPassword(hashedPassword);

    final var groupId = registerUserRequest.getGroupId();
    if (StrUtil.isNotBlank(groupId)) {
      final var sysGroup = sysGroupRepository.findById(groupId)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "用户组不存在"));
      ur.addGroup(sysGroup);
    }

    return userEntityRepository.save(ur);
  }
}
