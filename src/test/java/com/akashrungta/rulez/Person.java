package com.akashrungta.rulez;

import com.akashrungta.rulez.annotations.Operand;

public class Person {

  @Operand("age")
  private Integer age;

  @Operand("name")
  private String name;

  public Person(Integer age, String name) {
    this.age = age;
    this.name = name;
  }
}
