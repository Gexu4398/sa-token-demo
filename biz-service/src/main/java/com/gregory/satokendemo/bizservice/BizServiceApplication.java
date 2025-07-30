package com.gregory.satokendemo.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.gregory.satokendemo")
@Slf4j
public class BizServiceApplication {

  public static void main(String[] args) {

    SpringApplication.run(BizServiceApplication.class, args);
  }
}
