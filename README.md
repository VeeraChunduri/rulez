# Rulez

All the rules needs to defined in yaml document.
Below is the sample schema for rules definition. Rules needs to be separated by `---`

We have defined three rules below, two for group "a", and one for group "b":
* group "a" is valid if name is "akash", AND age is greater than 25.
* group "b" is valid if age greater than 50 OR less than 25.

```yaml
group: "a"
conditions:
  - operand: name
    operations:
      - operator: eq
        values:
          - akash
  - operand: age
    operations:
      - operator: gt
        values:
          - 25
---
group: "b"
conditions:
  - operand: age
    operations:
      - operator: gt
        values:
          - 50
      - operator: lt
        values:
          - 25

```    

There are four operators configured: `eq`,`ne`,`gt`,`lt`. 

We need to supply Operand-Applier for each operand we introduce. 

#Usages

Take the example class Person, we have @Operand for rulez to know which field to use to apply the condition.

```java
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
```

And to run the rulez
```java
    Rulez rulez = Rulez.create("rule.yaml");
    boolean retval = rulez.run(new Person(24, "akash"), group);

```