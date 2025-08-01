package com.gregory.satokendemo.ssoservice.service;

import com.gregory.satokendemo.ssomodel.model.UserEntity;
import com.gregory.satokendemo.ssoservice.model.NewUserRequest;
import com.gregory.satokendemo.ssoservice.model.UpdateUserRequest;
import java.util.List;

public interface UserService {

  UserEntity getUser(String id);

  UserEntity addUser(NewUserRequest newUserRequest);

  UserEntity updateUser(String id, UpdateUserRequest updateUserRequest);

  void enableUser(String id);

  void disableUser(List<String> ids);
}
