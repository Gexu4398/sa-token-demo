package com.gregory.satokendemo.bizservice.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StpInterfaceImpl implements StpInterface {

  @Override
  public List<String> getPermissionList(Object loginId, String loginType) {

    return StpUtil.getPermissionList();
  }

  @Override
  public List<String> getRoleList(Object loginId, String loginType) {

    return StpUtil.getRoleList();
  }
}
