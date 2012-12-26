package com.acme.sandbox;

import static org.junit.Assert.assertEquals;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;


public class ReflectionTest {

  @Test
  public void nameShouldIncludePackage() throws Exception {
    assertEquals("com.acme.sandbox.ReflectionTest", getClass().getName());
  }

  @Test
  public void shouldSetPropertiesWithBeanUtils() throws Exception {
    TestObject bean = new TestObject();

    @SuppressWarnings("unchecked")
    Map<String, Object> properties = BeanUtils.describe(bean);
    properties.put("prop1", "the prop");
    properties.put("prop2", 100);
    properties.put("prop3", 400);
    BeanUtils.populate(bean, properties);
    assertEquals(new TestObject("the prop", 100, 400), bean);

    // The conclusion of this test is that it has to be a public class and it
    // needs getters and setters.
  }
}

@Target({ElementType.METHOD, ElementType.FIELD})
@interface SomeObject {

}
