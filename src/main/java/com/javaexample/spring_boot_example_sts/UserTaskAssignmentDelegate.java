package com.javaexample.spring_boot_example_sts;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class UserTaskAssignmentDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    String assignedUser = (String) execution.getVariable("assignedUser");
    System.out.println("assigned user is: " + assignedUser);
  }
}
