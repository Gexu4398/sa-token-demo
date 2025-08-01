package com.gregory.satokendemo.ssoservice.model;

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

  private String roleId;
}
