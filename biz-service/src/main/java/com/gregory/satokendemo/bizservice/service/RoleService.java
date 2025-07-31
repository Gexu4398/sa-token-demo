package com.gregory.satokendemo.bizservice.service;

import java.util.List;

public interface RoleService {

  List<String> getRoleNames(String userId);

  List<String> getScopeNames(String userId);
}
