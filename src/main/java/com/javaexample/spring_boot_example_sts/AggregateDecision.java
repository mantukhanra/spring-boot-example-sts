package com.javaexample.spring_boot_example_sts;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AggregateDecision {

  @JobWorker(type = "aggregateDecision", autoComplete = true)
  public void aggregateDecision(final JobClient client, final ActivatedJob job) {

    Map<String, Object> variablesAsMap = (Map<String, Object>) job.getVariablesAsMap();

    String userDecisionCheck = (String) variablesAsMap.get("isCustomCheck");

    if (userDecisionCheck != null && userDecisionCheck.equalsIgnoreCase("equal")) {

      // throw new BpmnError("Equal", "value is equal to 100");
      variablesAsMap.put("User decision", "value is equal to 100");

    } else if (userDecisionCheck != null && userDecisionCheck.equalsIgnoreCase("less")) {

      // throw new BpmnError("Less", "value is less than 100");
      variablesAsMap.put("User decision", "value is less than 100");
    }

    client
        .newCompleteCommand(job.getKey())
        .variables(variablesAsMap)
        .send()
        .exceptionally(
            (throwable -> {
              throw new RuntimeException("Could not complete job", throwable);
            }));

    log.info("/* userDecisionCheck() executed */");

    log.info("/* aggregateFlow() executed */");
  }
}
