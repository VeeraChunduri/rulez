package com.akashrungta.rulez.annotations;

import com.akashrungta.rulez.applier.Applier;
import com.akashrungta.rulez.applier.ObjectApplier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Operand {

  String value();

  Class<? extends Applier> applier() default ObjectApplier.class;
  
}
