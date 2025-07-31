package com.gregory.satokendemo.bizservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gregory.satokendemo.bizmodel.model.UserEntity;
import com.gregory.satokendemo.bizmodel.repository.SysGroupRepository;
import com.gregory.satokendemo.bizmodel.repository.SysRoleRepository;
import com.gregory.satokendemo.bizmodel.repository.UserEntityRepository;
import com.gregory.satokendemo.bizservice.model.NewUserRequest;
import com.gregory.satokendemo.bizservice.model.RegisterUserRequest;
import com.gregory.satokendemo.bizservice.service.UserService;
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

  private final SysRoleRepository sysRoleRepository;

  @Autowired
  public UserServiceImpl(Argon2PasswordEncoder argon2PasswordEncoder,
      UserEntityRepository userEntityRepository, SysGroupRepository sysGroupRepository,
      SysRoleRepository sysRoleRepository) {

    this.argon2PasswordEncoder = argon2PasswordEncoder;

    this.userEntityRepository = userEntityRepository;
    this.sysGroupRepository = sysGroupRepository;
    this.sysRoleRepository = sysRoleRepository;
  }

  @Override
  public UserEntity addUser(@Nonnull NewUserRequest newUserRequest) {

    if (userEntityRepository.existsByUsername(newUserRequest.getUsername())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "账号已存在");
    }

    if (userEntityRepository.existsByEmail(newUserRequest.getEmail())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "邮箱已存在");
    }

    final var ur = new UserEntity();
    ur.setUsername(newUserRequest.getUsername());
    ur.setFirstName(newUserRequest.getName());
    ur.setEnabled(true);
    ur.setStatus(UserEntity.STATUS_NORMAL);
    ur.setPicture(newUserRequest.getPicture());
    ur.setEmail(newUserRequest.getEmail());
    ur.setPhoneNumber(newUserRequest.getPhoneNumber());

    final var hashedPassword = argon2PasswordEncoder.encode(newUserRequest.getPassword());
    ur.setPassword(hashedPassword);

    if (StrUtil.isNotBlank(newUserRequest.getGroupId())) {
      final var sysGroup = sysGroupRepository.findById(newUserRequest.getGroupId())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "用户组不存在"));
      ur.addGroup(sysGroup);
    }
    if (StrUtil.isNotBlank(newUserRequest.getRoleId())) {
      final var sysRole = sysRoleRepository.findById(newUserRequest.getRoleId())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "角色不存在"));
      ur.addRole(sysRole);
    }

    return userEntityRepository.save(ur);
  }

  @Override
  public UserEntity registerUser(@Nonnull RegisterUserRequest registerUserRequest) {

    if (userEntityRepository.existsByUsername(registerUserRequest.getUsername())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "账号已存在");
    }

    if (userEntityRepository.existsByEmail(registerUserRequest.getEmail())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "邮箱已存在");
    }

    final var ur = new UserEntity();
    ur.setUsername(registerUserRequest.getUsername());
    ur.setFirstName(registerUserRequest.getName());
    ur.setEnabled(true);
    ur.setStatus(UserEntity.STATUS_PENDING);
    ur.setPicture(registerUserRequest.getPicture());
    ur.setEmail(registerUserRequest.getEmail());
    ur.setPhoneNumber(registerUserRequest.getPhoneNumber());

    final var hashedPassword = argon2PasswordEncoder.encode(registerUserRequest.getPassword());
    ur.setPassword(hashedPassword);

    if (StrUtil.isNotBlank(registerUserRequest.getGroupId())) {
      final var sysGroup = sysGroupRepository.findById(registerUserRequest.getGroupId())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "用户组不存在"));
      ur.addGroup(sysGroup);
    }

    return userEntityRepository.save(ur);
  }
}
