package com.gregory.satokendemo.bizservice.service.impl;

import com.gregory.satokendemo.bizmodel.repository.ClientScopeRepository;
import com.gregory.satokendemo.bizmodel.repository.SysRoleRepository;
import com.gregory.satokendemo.bizservice.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  private final SysRoleRepository sysRoleRepository;

  private final ClientScopeRepository clientScopeRepository;

  @Autowired
  public RoleServiceImpl(SysRoleRepository sysRoleRepository,
      ClientScopeRepository clientScopeRepository) {

    this.sysRoleRepository = sysRoleRepository;
    this.clientScopeRepository = clientScopeRepository;
  }

  @Override
  public List<String> getRoleNames(String userId) {

    return sysRoleRepository.findDistinctNameByUsers_Id(userId);
  }

  @Override
  public List<String> getScopeNames(String userId) {

    return clientScopeRepository.findDistinctNameByRoles_Users_Id(userId);
  }
}
