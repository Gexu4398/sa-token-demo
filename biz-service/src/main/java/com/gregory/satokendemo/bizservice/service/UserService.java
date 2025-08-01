package com.gregory.satokendemo.bizservice.service;

import com.gregory.satokendemo.bizservice.model.RegisterUserRequest;
import com.gregory.satokendemo.ssomodel.model.UserEntity;

public interface UserService {

  UserEntity registerUser(RegisterUserRequest registerUserRequest);
}
