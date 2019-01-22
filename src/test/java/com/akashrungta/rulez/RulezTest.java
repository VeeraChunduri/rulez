package com.akashrungta.rulez;

import org.junit.Assert;
import org.junit.Test;

public class RulezTest {

  public static final String groupARule = "a";

  @Test
  public void testFail1(){
    Rulez rulez = Rulez.create("my_rule.yaml");
    boolean retA = rulez.run(new Person(24, "akash"), groupARule);
    Assert.assertEquals(false, retA);
  }

  @Test
  public void testFail2(){
    Rulez rulez = Rulez.create("my_rule.yaml");
    boolean retA = rulez.run(new Person(26, "ayush"), groupARule);
    Assert.assertEquals(false, retA);
  }

  @Test
  public void testPass1(){
    Rulez rulez = Rulez.create("my_rule.yaml");
    boolean retA = rulez.run(new Person(26, "akash"), groupARule);
    Assert.assertEquals(true, retA);
  }

}