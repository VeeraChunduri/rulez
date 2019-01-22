package com.akashrungta.rulez;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.stream.Collectors;

public class Rulez {

  public static Rulez create(String yamlFile) {
    try {
      return new Rulez(yamlFile);
    } catch (FileNotFoundException e){
      throw new RuntimeException("Rule definition file " + yamlFile + " not found");
    }
  }

  public boolean run(Object value, String group){
    return evaluator.evaluate(value, ruleProvider.fetchBygroup(group));
  }

  public Map<String, Boolean> run(Object value){
    return ruleProvider.fetchAll().asMap().entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> evaluator.evaluate(value, e.getValue())));
  }

  private RuleProvider ruleProvider;

  private Evaluator evaluator;

  private Rulez(String yamlFile) throws FileNotFoundException {
    this.ruleProvider = new RuleProvider(yamlFile);
    this.evaluator = new Evaluator();
  }

}
