package com.gregory.satokendemo.bizservice.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotSuperAdminRoleIdValidator.class)
@Documented
public @interface NotSuperAdminRoleId {

  String message() default "不能创建超级管理员角色的账号！";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
