package com.acme.sandbox;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;

public class GuiceTest {

  @Test
  public void itShouldQuack() throws Exception {
    Runner runner = Guice.createInjector(new DuckModule()).getInstance(Runner.class);
    assertEquals("quack", runner.doIt());
  }

  @Test
  public void itShouldAlsoQuack() throws Exception {
    Injector injector = Guice.createInjector(new DuckModule());

    // works
    // @SuppressWarnings("unchecked")
    // Duck<String> duck = injector.getInstance(Duck.class);

    // also works
    Duck<String> duck = injector.getInstance(Key.get(new TypeLiteral<Duck<String>>() { }));
    assertEquals("quack", duck.speak());
  }

}

interface Speaker<T extends CharSequence> {
  T speak();
}

class Duck<T extends CharSequence> implements Speaker<T> {
  @Override
  @SuppressWarnings("unchecked")
  public T speak() {
    return (T) "quack";
  }
}

class DuckModule extends AbstractModule {
  @Override
  protected void configure() {
//    bind(Speaker.class).to(Duck.class);
    bind(new TypeLiteral<Speaker<String>>(){})
        .to(new TypeLiteral<Duck<String>>(){})
        .in(Scopes.SINGLETON);
  }
}

class Runner {
  private final Speaker<String> speaker;

  @Inject
  public Runner(Speaker<String> speaker) {
    this.speaker = speaker;
  }

  public String doIt() {
    return this.speaker.speak();
  }
}