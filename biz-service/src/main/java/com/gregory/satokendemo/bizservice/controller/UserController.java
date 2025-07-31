package com.gregory.satokendemo.bizservice.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gregory.satokendemo.bizmodel.model.UserEntity;
import com.gregory.satokendemo.bizservice.model.NewUserRequest;
import com.gregory.satokendemo.bizservice.service.UserService;
import com.gregory.satokendemo.bizservice.validator.NotContainsSuperAdminUserId;
import com.gregory.satokendemo.bizservice.validator.NotSuperAdminUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户管理")
@RestController
@RequestMapping("user")
@Validated
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {

    this.userService = userService;
  }

  @Operation(summary = "新建用户")
  @PostMapping
//  @SaCheckPermission("user:crud")
  @Transactional("bizTransactionManager")
  public UserEntity addUser(@Valid @RequestBody NewUserRequest newUserRequest) {

    return userService.addUser(newUserRequest);
  }

  @SneakyThrows
  @PostMapping("/{id}:enable")
  @Operation(summary = "启用用户")
  @SaCheckPermission("user:crud")
  @Transactional("bizTransactionManager")
  public void enableUser(@NotSuperAdminUserId @PathVariable("id") String id) {

    userService.enableUser(id);
  }

  @SneakyThrows
  @PostMapping("/{id}:disable")
  @Operation(summary = "停用用户")
  @SaCheckPermission("user:crud")
  @Transactional("bizTransactionManager")
  public void disableUser(@NotContainsSuperAdminUserId @PathVariable("id") List<String> ids) {

    userService.disableUser(ids);
  }
}
