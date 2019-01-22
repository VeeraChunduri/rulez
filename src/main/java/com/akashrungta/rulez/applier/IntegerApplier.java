package com.akashrungta.rulez.applier;

import com.akashrungta.rulez.models.Operator;
import com.akashrungta.rulez.models.Rule.Condition.Operation;
import java.util.Set;

public class IntegerApplier implements Applier<Integer> {

  @Override
  public boolean apply(Integer input, Set<Operation> operations) {
    return operations
        .stream()
        .map(op -> apply(input, op.getOperator(), op.getValues()))
        .reduce((x, y) -> x && y)
        .orElse(false);

  }

  @Override
  public boolean apply(Integer input, Operator operator, Set<Object> values) {
    switch (operator) {
      case gt:
        return values.stream().allMatch(v -> input.compareTo((Integer) v) > 0);
      case lt:
        return values.stream().allMatch(v -> input.compareTo((Integer) v) < 0);
      case eq:
        return values.stream().allMatch(v -> input.compareTo((Integer) v) == 0);
      case ne:
        return values.stream().allMatch(v -> input.compareTo((Integer) v) != 0);
      default:
        throw new UnsupportedOperationException(
            "Invalid Operator configured for the " + getClass().getSimpleName());
    }
  }

}
