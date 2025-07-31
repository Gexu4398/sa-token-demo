package com.gregory.satokendemo.bizservice.validator;

import com.gregory.satokendemo.bizservice.service.UserService;
import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NotSuperAdminUserIdValidator implements
    ConstraintValidator<NotSuperAdminUserId, String> {

  private final String userId;

  @Autowired
  public NotSuperAdminUserIdValidator(@Nonnull UserService userService) {

    userId = userService.getUserByName("admin").getId();
  }

  @Override
  public void initialize(NotSuperAdminUserId constraintAnnotation) {

    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    return !userId.equalsIgnoreCase(value);
  }
}
