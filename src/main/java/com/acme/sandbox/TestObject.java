package com.acme.sandbox;

import com.google.common.base.Objects;

public class TestObject {
  public String prop1;
  public long prop2;
  private int prop3;

  public TestObject() {
    // pass
  }

  public TestObject(String prop1, long prop2, int prop3) {
    this.prop1 = prop1;
    this.prop2 = prop2;
    this.prop3 = prop3;
  }

  @MyAnnotation(whatInTheActualFuck = {"A"})
  public String getProp1() {
    return prop1;
  }

  public void setProp1(String prop1) {
    this.prop1 = prop1;
  }

  @MyAnnotation()
  public long getProp2() {
    return prop2;
  }

  public void setProp2(long prop2) {
    this.prop2 = prop2;
  }

  public int getProp3() {
    return prop3;
  }

  public void setProp3(int prop3) {
    this.prop3 = prop3;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((prop1 == null) ? 0 : prop1.hashCode());
    result = prime * result + (int) (prop2 ^ (prop2 >>> 32));
    result = prime * result + prop3;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof TestObject))
      return false;
    TestObject other = (TestObject) obj;
    if (prop1 == null) {
      if (other.prop1 != null)
        return false;
    } else if (!prop1.equals(other.prop1))
      return false;
    if (prop2 != other.prop2)
      return false;
    if (prop3 != other.prop3)
      return false;
    return true;
  }


  @Override
  public String toString() {
    return Objects.toStringHelper(getClass())
        .add("prop1", prop1)
        .add("prop2", prop2)
        .add("prop3", prop3)
        .toString();
  }
}