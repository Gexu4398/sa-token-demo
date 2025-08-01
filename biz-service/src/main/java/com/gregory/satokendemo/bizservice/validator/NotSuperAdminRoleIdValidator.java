package com.gregory.satokendemo.bizservice.validator;

import com.gregory.satokendemo.ssomodel.repository.SysRoleRepository;
import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NotSuperAdminRoleIdValidator implements
    ConstraintValidator<NotSuperAdminRoleId, String> {

  private final String roleId;

  @Autowired
  public NotSuperAdminRoleIdValidator(@Nonnull SysRoleRepository sysRoleRepository) {

    roleId = sysRoleRepository.findByName("超级管理员").getId();
  }

  @Override
  public void initialize(NotSuperAdminRoleId constraintAnnotation) {

    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    return !roleId.equalsIgnoreCase(value);
  }
}
