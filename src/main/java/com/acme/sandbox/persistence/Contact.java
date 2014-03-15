package com.acme.sandbox.persistence;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.acme.sandbox.common.MoreStrings;
import com.google.common.base.Objects;

@PersistenceCapable
public class Contact {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
  public long id;

  @Persistent
  public Address address;

  @Persistent
  public String email;

  @Persistent
  public String name;

  @Persistent
  public String phoneNumber;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Contact other = (Contact) obj;
    return Objects.equal(id, other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return new MoreStrings().stringify(this);
  }
}
