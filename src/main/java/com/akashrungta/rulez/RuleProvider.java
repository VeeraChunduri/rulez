package com.akashrungta.rulez;

import com.akashrungta.rulez.models.Rule;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class RuleProvider {

  private Multimap<String, Rule> groupToRules;

  public RuleProvider(String yamlFile)
      throws FileNotFoundException {
    this.groupToRules = loadYaml(yamlFile);
  }

  private Multimap<String, Rule> loadYaml(String serviceRuleYaml)
      throws FileNotFoundException {
    Multimap<String, Rule> retval = HashMultimap.create();
    Yaml yaml = new Yaml(new Constructor(Rule.class));
    InputStream inputStream =
        new FileInputStream(
            new File(getClass().getClassLoader().getResource(serviceRuleYaml).getFile()));
    for (Object object : yaml.loadAll(inputStream)) {
      Rule sr = (Rule) object;
      validate(sr);
      retval.put(sr.getGroup(), sr);
    }
    return retval;
  }

  // TODO: Validate service rules during application start
  private void validate(Rule serviceRule) {}


  public Multimap<String, Rule> fetchAll() {
    return groupToRules;
  }

  public Collection<Rule> fetchBygroup(String group){
    return groupToRules.get(group);
  }

}
