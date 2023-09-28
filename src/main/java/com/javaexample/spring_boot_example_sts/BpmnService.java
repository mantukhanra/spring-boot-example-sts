package com.javaexample.spring_boot_example_sts;

import io.camunda.operate.exception.OperateException;
import io.camunda.tasklist.exception.TaskListException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@Service
@EnableCaching
public class BpmnService {

  /**
   * If true, we are getting the schema from TaskList. if false, we try to read the schema from the
   * local BPMN file.
   */
  @Value("${embeddedForms.fromLocalResources:true}")
  private Boolean loadEmbeddedFormsLocally;

  @Autowired private OperateService operateService;

  @Cacheable("processEmbeddedForms")
  public String getEmbeddedFormSchema(
      String bpmnProcessId, String processDefinitionId, String formId)
      throws TaskListException, NumberFormatException, OperateException {
    if (loadEmbeddedFormsLocally && bpmnProcessId != null) {
      String schema = BpmnUtils.getFormSchemaFromFile(bpmnProcessId + ".bpmn", formId);

      if (schema != null) {
        return schema;
      }
    }
    String xml = operateService.getProcessDefinitionXmlByKey(Long.valueOf(processDefinitionId));
    return BpmnUtils.getFormSchemaFromBpmn(xml, formId);
  }

  @Cacheable("processTaskNames")
  public String getTaskName(String bpmnProcessId, String processDefinitionId, String activityId)
      throws NumberFormatException, OperateException {

    if (loadEmbeddedFormsLocally && bpmnProcessId != null) {
      return BpmnUtils.getTaskNameFromFile("value_check_diagram_1" + ".bpmn", activityId);
    }
    String xml = operateService.getProcessDefinitionXmlByKey(Long.valueOf(processDefinitionId));
    return BpmnUtils.getTaskNameFromBpmn(xml, activityId);
  }

  @Cacheable("processNames")
  public String getProcessName(String bpmnProcessId, String processDefinitionId)
      throws NumberFormatException, OperateException {

    if (loadEmbeddedFormsLocally && bpmnProcessId != null) {
      return BpmnUtils.getProcessNameFromFile("value_check_diagram_1" + ".bpmn", bpmnProcessId);
    }
    String xml = operateService.getProcessDefinitionXmlByKey(Long.valueOf(processDefinitionId));
    return BpmnUtils.getTaskNameFromBpmn(xml, bpmnProcessId);
  }
}
