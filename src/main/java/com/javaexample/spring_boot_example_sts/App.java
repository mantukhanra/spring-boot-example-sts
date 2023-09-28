package com.javaexample.spring_boot_example_sts;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Hello world! */
@RestController
@RequestMapping("/sts")
@Slf4j
@CrossOrigin
public class App {
  @Autowired private ZeebeClient client;

  @Async("asyncExecutor")
  @GetMapping("/value/{value}")
  public String index(@PathVariable int value) {
    log.info("ok");

    HashMap<String, Object> variables = new HashMap<String, Object>();
    variables.put("value", value);

    ProcessInstanceEvent processInstanceEvent =
        client
            .newCreateInstanceCommand()
            .bpmnProcessId("demo-sevice-1")
            .latestVersion()
            .variables(variables)
            .send()
            .join();

    log.info("process id{}: ", processInstanceEvent.getBpmnProcessId());
    return processInstanceEvent.getBpmnProcessId();
  }
}
