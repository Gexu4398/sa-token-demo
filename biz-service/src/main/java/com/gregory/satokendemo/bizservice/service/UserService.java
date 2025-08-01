package com.gregory.satokendemo.bizservice.service;

import com.gregory.satokendemo.bizservice.model.NewUserRequest;
import com.gregory.satokendemo.bizservice.model.RegisterUserRequest;
import com.gregory.satokendemo.bizservice.model.UpdateUserRequest;
import com.gregory.satokendemo.ssomodel.model.UserEntity;
import java.util.List;

public interface UserService {

  UserEntity getUser(String id);

  UserEntity getUserByName(String username);

  UserEntity addUser(NewUserRequest newUserRequest);

  UserEntity updateUser(String id, UpdateUserRequest updateUserRequest);

  UserEntity registerUser(RegisterUserRequest registerUserRequest);

  void enableUser(String id);

  void disableUser(List<String> ids);
}
