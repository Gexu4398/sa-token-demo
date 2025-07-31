package com.gregory.satokendemo.bizservice.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.gregory.satokendemo.bizservice.service.RoleService;
import jakarta.annotation.Nonnull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StpInterfaceImpl implements StpInterface {

  private final RoleService roleService;

  @Autowired
  public StpInterfaceImpl(RoleService roleService) {

    this.roleService = roleService;
  }

  @Override
  public List<String> getPermissionList(@Nonnull Object loginId, String loginType) {

    return roleService.getScopeNames(String.valueOf(loginId));
  }

  @Override
  public List<String> getRoleList(@Nonnull Object loginId, String loginType) {

    return roleService.getRoleNames(String.valueOf(loginId));
  }
}
