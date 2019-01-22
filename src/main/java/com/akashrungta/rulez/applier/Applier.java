package com.akashrungta.rulez.applier;

import com.akashrungta.rulez.models.Operator;
import com.akashrungta.rulez.models.Rule.Condition.Operation;
import java.util.Set;

public interface Applier<F> {

  boolean apply(F f, Set<Operation> operations);

  boolean apply(F f, Operator operator, Set<Object> values);

}
