package com.javaexample.spring_boot_example_sts;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableZeebeClient
@Deployment(resources = {"bpmn/value_check_diagram_1.bpmn"})
public class SpringBootExampleSts {
  public static void main(String[] args) {
    SpringApplication.run(SpringBootExampleSts.class, args);
  }
}
