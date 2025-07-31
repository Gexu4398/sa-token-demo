package com.gregory.satokendemo.bizservice.service;

import com.gregory.satokendemo.bizmodel.model.UserEntity;
import com.gregory.satokendemo.bizservice.model.NewUserRequest;
import com.gregory.satokendemo.bizservice.model.RegisterUserRequest;

public interface UserService {

  UserEntity addUser(NewUserRequest newUserRequest);

  UserEntity registerUser(RegisterUserRequest registerUserRequest);
}
