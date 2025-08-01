package com.gregory.satokendemo.bizservice.model;

import com.gregory.satokendemo.bizservice.validator.NotSuperAdminRoleId;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {

  private String name;

  private String email;

  private String phoneNumber;

  private String picture;

  private String groupId;

  @NotSuperAdminRoleId
  private String roleId;
}
