package com.javaexample.spring_boot_example_sts;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendEmailService {

  @JobWorker(type = "sendTaskEmail", autoComplete = true)
  public void aggregateDecision(final JobClient client, final ActivatedJob job) {

    Map<String, Object> variables = (Map<String, Object>) job.getVariablesAsMap();
    int value = (int) variables.get("value");

    if (value > 100) {
      variables.put("isMoreThanHundred", true);
    } else {
      variables.put("isMoreThanHundred", false);
    }

    client
        .newCompleteCommand(job.getKey())
        .variables(variables)
        .send()
        .exceptionally(
            (throwable -> {
              throw new RuntimeException("Could not complete job", throwable);
            }));

    log.info("/* checkValueService() executed */");

    log.info("/* aggregateFlow() executed */");
  }
}
