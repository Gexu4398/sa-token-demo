package com.gregory.satokendemo.bizservice.validator;

import com.gregory.satokendemo.bizservice.service.UserService;
import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;

public class NotContainsSuperAdminUserIdValidator implements
    ConstraintValidator<NotContainsSuperAdminUserId, Collection<String>> {

  private final String userId;

  @Autowired
  public NotContainsSuperAdminUserIdValidator(@Nonnull UserService userService) {

    userId = userService.getUserByName("admin").getId();
  }

  @Override
  public void initialize(NotContainsSuperAdminUserId constraintAnnotation) {

    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(Collection<String> value, ConstraintValidatorContext context) {

    return value.stream().noneMatch(it -> it.equalsIgnoreCase(userId));
  }
}
