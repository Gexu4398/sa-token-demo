package com.gregory.satokendemo.ssoservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewUserRequest {

  @NotBlank
  @Schema(description = "用户名")
  private String username;

  @Schema(description = "密码")
  private String password;

  @Schema(description = "姓")
  private String firstName;

  @Schema(description = "名")
  private String lastName;

  @Schema(description = "邮箱")
  private String email;

  @Schema(description = "手机号码")
  private String phoneNumber;

  @Schema(description = "头像")
  private String picture;

  @Schema(description = "用户组id")
  private String groupId;

  @Schema(description = "角色id")
  private String roleId;
}
