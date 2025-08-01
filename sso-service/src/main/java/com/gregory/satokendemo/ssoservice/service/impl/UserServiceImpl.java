package com.gregory.satokendemo.ssoservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gregory.satokendemo.ssomodel.model.UserEntity;
import com.gregory.satokendemo.ssomodel.repository.SysGroupRepository;
import com.gregory.satokendemo.ssomodel.repository.SysRoleRepository;
import com.gregory.satokendemo.ssomodel.repository.UserEntityRepository;
import com.gregory.satokendemo.ssoservice.model.NewUserRequest;
import com.gregory.satokendemo.ssoservice.model.UpdateUserRequest;
import com.gregory.satokendemo.ssoservice.service.UserService;
import jakarta.annotation.Nonnull;
import java.util.List;
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
  public UserEntity getUser(String id) {

    return userEntityRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在"));
  }

  @Override
  public UserEntity addUser(@Nonnull NewUserRequest newUserRequest) {

    if (userEntityRepository.existsByUsername(newUserRequest.getUsername())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "账号已存在");
    }

    if (StrUtil.isNotBlank(newUserRequest.getEmail()) &&
        userEntityRepository.existsByEmail(newUserRequest.getEmail())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "邮箱已存在");
    }

    final var ur = new UserEntity();
    ur.setUsername(newUserRequest.getUsername());
    ur.setFirstName(newUserRequest.getFirstName());
    ur.setLastName(newUserRequest.getLastName());
    ur.setEnabled(true);
    ur.setStatus(UserEntity.STATUS_NORMAL);
    ur.setPicture(newUserRequest.getPicture());
    ur.setEmail(newUserRequest.getEmail());
    ur.setPhoneNumber(newUserRequest.getPhoneNumber());

    final var hashedPassword = argon2PasswordEncoder.encode(newUserRequest.getPassword());
    ur.setPassword(hashedPassword);

    final var groupId = newUserRequest.getGroupId();
    if (StrUtil.isNotBlank(groupId)) {
      final var sysGroup = sysGroupRepository.findById(groupId)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "用户组不存在"));
      ur.addGroup(sysGroup);
    }

    final var roleId = newUserRequest.getRoleId();
    if (StrUtil.isNotBlank(roleId)) {
      final var sysRole = sysRoleRepository.findById(roleId)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "角色不存在"));
      ur.addRole(sysRole);
    }

    return userEntityRepository.save(ur);
  }

  @Override
  public UserEntity updateUser(String id, @Nonnull UpdateUserRequest updateUserRequest) {

    final var user = getUser(id);

    final var email = updateUserRequest.getEmail();

    if (StrUtil.isNotBlank(email) && !StrUtil.equals(email, user.getEmail())) {
      validateEmailUnique(email, user.getId());
    }

    user.setFirstName(updateUserRequest.getName());
    user.setEmail(email);
    user.setPhoneNumber(updateUserRequest.getPhoneNumber());
    user.setPicture(updateUserRequest.getPicture());

    return userEntityRepository.save(user);
  }

  @Override
  public void enableUser(String id) {

    final var user = getUser(id);
    user.setEnabled(true);
    user.setStatus(UserEntity.STATUS_NORMAL);
    userEntityRepository.save(user);
  }

  @Override
  public void disableUser(List<String> ids) {

    final var userEntities = userEntityRepository.findAllById(ids);
    userEntities.forEach(it -> {
      it.setEnabled(false);
      it.setStatus(UserEntity.STATUS_DISABLED);
    });
    userEntityRepository.saveAll(userEntities);
  }

  private void validateEmailUnique(String email, String userId) {

    userEntityRepository.findByEmail(email)
        .filter(it -> !it.getId().equals(userId))
        .ifPresent(it -> {
          throw new ResponseStatusException(HttpStatus.CONFLICT, "邮箱已存在");
        });
  }
}
