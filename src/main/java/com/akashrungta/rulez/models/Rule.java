package com.akashrungta.rulez.models;

import java.util.Set;
import lombok.Data;

@Data
public class Rule {

  String group;
  Set<Condition> conditions;

  @Data
  public static class Condition {
    String operand;
    Set<Operation> operations;

    @Data
    public static class Operation {
      Operator operator;
      Set<Object> values;
    }
  }
}
