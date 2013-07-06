package com.acme.sandbox;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.acme.sandbox.Stringifier.Format;
import com.acme.sandbox.Stringifier.Stringifiable;

public class StringifierTest {

  @Test
  public void shouldStringifyToYaml() throws Exception {
    assertEquals(
        String.format(
            "b:%n" +
            "  anotherField: this is another, field%n" +
            "  c: {x: true}%n" +
            "  number: 110%n" +
            "fieldA: yup%n" +
            "fieldB: 100%n" +
            "fieldC: cool%n"),
        new Stringifier().stringify(newObject(), Format.YAML));
  }

  @Test
  public void shouldStringifyToJson() throws Exception {
    assertEquals(
        String.format(
            "{%n" +
            "  \"fieldA\" : \"yup\",%n" +
            "  \"fieldB\" : 100,%n" +
            "  \"fieldC\" : \"cool\",%n" +
            "  \"shouldBeNull\" : null,%n" +
            "  \"b\" : {%n" +
            "    \"anotherField\" : \"this is another, field\"%n" +
            "  }%n" +
            "}"),
        new Stringifier().stringify(newObject()));
  }

  private AA newObject() {
    AA a = new AA();
    a.setFieldA("yup");
    a.setFieldB(100L);
    a.fieldC = "cool";
    a.setB(new B());
    a.getB().setAnotherField("this is another, field");
    a.getB().number = 110;
    a.getB().c = new C();
    a.getB().c.x = true;
    return a;
  }

  @Stringifiable
  public static class A {
    @SuppressWarnings("unused")
    private static final String A = "A";
    private String fieldA;
    private long fieldB;
    public Object fieldC;
    public Object shouldBeNull;
    public String getFieldA() {
      return fieldA;
    }
    public void setFieldA(String fieldA) {
      this.fieldA = fieldA;
    }
    public long getFieldB() {
      return fieldB;
    }
    public void setFieldB(long fieldB) {
      this.fieldB = fieldB;
    }
  }

  @Stringifiable
  public static class AA extends A {
    private B b;
    public B getB() {
      return b;
    }
    public void setB(B b) {
      this.b = b;
    }
  }

  @Stringifiable
  public static class B {
    private String anotherField;
    int number;
    C c;

    public String getAnotherField() {
      return anotherField;
    }
    public void setAnotherField(String anotherField) {
      this.anotherField = anotherField;
    }
  }

  @Stringifiable
  public static class C {
    boolean x;
  }
}
