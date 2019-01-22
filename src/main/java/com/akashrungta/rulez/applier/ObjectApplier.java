package com.akashrungta.rulez.applier;

import com.akashrungta.rulez.models.Operator;
import com.akashrungta.rulez.models.Rule.Condition.Operation;
import java.util.Set;

public class ObjectApplier implements Applier<Object> {

  @Override
  public boolean apply(Object o, Set<Operation> operations) {
    return operations
        .stream()
        .map(op -> apply(o, op.getOperator(), op.getValues()))
        .reduce((x, y) -> x && y)
        .orElse(false);
  }

  @Override
  public boolean apply(Object o, Operator operator, Set<Object> values) {
    switch (operator) {
      case eq:
        return values.contains(o);
      case ne:
        return !values.contains(o);
      default:
        throw new UnsupportedOperationException(
            "Invalid Operator configured for the " + getClass().getSimpleName());
    }
  }
}
