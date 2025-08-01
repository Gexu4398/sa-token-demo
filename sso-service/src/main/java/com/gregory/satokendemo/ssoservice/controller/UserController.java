package com.gregory.satokendemo.ssoservice.controller;

import com.gregory.satokendemo.ssomodel.model.UserEntity;
import com.gregory.satokendemo.ssoservice.model.NewUserRequest;
import com.gregory.satokendemo.ssoservice.model.UpdateUserRequest;
import com.gregory.satokendemo.ssoservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @Operation(summary = "获取用户详情")
  @GetMapping("{id}")
  public UserEntity getUser(@PathVariable("id") String id) {

    return userService.getUser(id);
  }

  @Operation(summary = "新建用户")
  @PostMapping
  @Transactional("ssoTransactionManager")
  public UserEntity addUser(@Valid @RequestBody NewUserRequest newUserRequest) {

    return userService.addUser(newUserRequest);
  }

  @Operation(summary = "编辑用户")
  @PutMapping("{id}")
  @Transactional("ssoTransactionManager")
  public UserEntity updateUser(@PathVariable("id") String id,
      @Valid @RequestBody UpdateUserRequest updateUserRequest) {

    return userService.updateUser(id, updateUserRequest);
  }

  @PostMapping("/{id}:enable")
  @Operation(summary = "启用用户")
  @Transactional("ssoTransactionManager")
  public void enableUser(@PathVariable("id") String id) {

    userService.enableUser(id);
  }

  @PostMapping("/{id}:disable")
  @Operation(summary = "停用用户")
  @Transactional("ssoTransactionManager")
  public void disableUser(@PathVariable("id") List<String> ids) {

    userService.disableUser(ids);
  }
}
