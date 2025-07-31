package com.gregory.satokendemo.ssoservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.gregory.satokendemo")
@Slf4j
public class SsoServiceApplication {

  public static void main(String[] args) {

    SpringApplication.run(SsoServiceApplication.class, args);
  }
}
