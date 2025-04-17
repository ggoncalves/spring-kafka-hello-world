package com.ggoncalves.eventdriven.hello.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// @ComponentScan is automatically added in @SpringBootApplication. But it would start scanning from current package.
// And considering this (current) package is `main`, we should explicitly define the root package as `basePackage`.
@SpringBootApplication
@ComponentScan(basePackages = {"com.ggoncalves.eventdriven.hello"})
public class ProducerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProducerApplication.class, args);
  }

}