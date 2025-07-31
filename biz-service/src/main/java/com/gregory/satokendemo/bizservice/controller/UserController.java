package com.gregory.satokendemo.bizservice.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gregory.satokendemo.bizmodel.model.UserEntity;
import com.gregory.satokendemo.bizservice.model.NewUserRequest;
import com.gregory.satokendemo.bizservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
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
  @Transactional("bizTransactionManager")
  @SaCheckPermission("user:crud")
  public UserEntity addUser(@Valid @RequestBody NewUserRequest newUserRequest) {

    return userService.addUser(newUserRequest);
  }
}
