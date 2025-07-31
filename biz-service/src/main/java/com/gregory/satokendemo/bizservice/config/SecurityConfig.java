package com.gregory.satokendemo.bizservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
public class SecurityConfig {

  @Bean
  public Argon2PasswordEncoder argon2PasswordEncoder() {

    return new Argon2PasswordEncoder(16, 32, 1, 7168, 5);
  }
}
