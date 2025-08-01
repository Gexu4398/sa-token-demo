package com.gregory.satokendemo.bizservice.controller;

import com.gregory.satokendemo.bizservice.service.UserService;
import com.gregory.satokendemo.ssomodel.model.UserEntity;
import com.gregory.satokendemo.bizservice.model.RegisterUserRequest;
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

@Tag(name = "新用户注册")
@RestController
@RequestMapping("register")
@Validated
public class RegisterController {

  private final UserService userService;

  @Autowired
  public RegisterController(UserService userService) {

    this.userService = userService;
  }

  @PostMapping
  @Operation(summary = "新用户注册")
  @Transactional("ssoTransactionManager")
  public UserEntity register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {

    return userService.registerUser(registerUserRequest);
  }
}
