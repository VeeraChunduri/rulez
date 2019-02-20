package com.akashrungta.rulez;

import com.akashrungta.rulez.annotations.Operand;
import com.akashrungta.rulez.applier.Applier;
import com.akashrungta.rulez.applier.IntegerApplier;
import com.akashrungta.rulez.applier.ObjectApplier;
import com.akashrungta.rulez.models.Rule;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Evaluator {


  /*
    Evaluate all the rules for args
    Returns TRUE if any of rule is satisfied
     */
  public boolean evaluate(Object v, Collection<Rule> rules) {
    return rules
        .stream()
        .map(rule -> evaluateConditions(v, rule.getConditions()))
        .reduce((x, y) -> x || y)
        .orElse(true);
  }

  /*
  All the conditions within the Rules must be TRUE
  If no conditions are defined, the rule is true
   */
  private boolean evaluateConditions(Object v, Set<Rule.Condition> conditions) {
    Map<String, Pair<Object, Class>> annotationToProperty = Maps.newHashMap();
    Arrays.stream(v.getClass().getDeclaredFields())
        .filter(f -> f.isAnnotationPresent(Operand.class))
        .forEach(
            f -> {
              Operand op = f.getDeclaredAnnotation(Operand.class);
              try {
                f.setAccessible(true);
                Object fv = f.get(v);
                Class<? extends Applier> applierClazz = op.applier();
                // if default applier, see if we can override it to primitive applier
                if (op.applier() == ObjectApplier.class) {
                  if (fv.getClass().isAssignableFrom(Integer.class)) {
                    applierClazz = IntegerApplier.class;
                  }
                }
                annotationToProperty.put(op.value(), Pair.of(fv, applierClazz));
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              }
            });
    return conditions
        .stream()
        .filter(condition -> annotationToProperty.containsKey(condition.getOperand()))
        .map(
            condition -> {
              try {
                Pair<Object, Class> pairOfValueToClazz = annotationToProperty.get(condition.getOperand());
                Applier applier = (Applier) pairOfValueToClazz.getRight().newInstance();
                return applier.apply(pairOfValueToClazz.getLeft(), condition.getOperations());
              } catch (Exception e) {
                e.printStackTrace();
              }
              return false;
            })
        .reduce((x, y) -> x && y)
        .orElse(true);
  }
}
