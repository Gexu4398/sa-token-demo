package com.gregory.satokendemo.bizservice.service;

import com.gregory.satokendemo.bizmodel.model.UserEntity;
import com.gregory.satokendemo.bizservice.model.NewUserRequest;
import com.gregory.satokendemo.bizservice.model.RegisterUserRequest;
import java.util.List;

public interface UserService {

  UserEntity getUser(String id);

  UserEntity getUserByName(String username);

  UserEntity addUser(NewUserRequest newUserRequest);

  UserEntity registerUser(RegisterUserRequest registerUserRequest);

  void enableUser(String id);

  void disableUser(List<String> ids);
}
