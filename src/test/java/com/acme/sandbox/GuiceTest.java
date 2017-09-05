package com.acme.sandbox;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;

import com.google.inject.*;
import org.junit.Test;

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

    Human human = injector.getInstance(Human.class);
    assertEquals("bark", human.walkDog());
  }

  @Test
  public void itShouldMeow() throws Exception {
    Injector injector = Guice.createInjector(new CatModule());

    Provider<Cat> catProvider = injector.getProvider(Cat.class);
    assertThat(catProvider.get()).isNotSameAs(catProvider.get());
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

class Dog {
  public String bark() {
    return "bark";
  }
}

class DogProvider implements Provider<Dog> {
  @Override
  public Dog get() {
    return new Dog();
  }
}

class Cat implements Speaker<String> {
  @Override
  public String speak() {
    return "meow";
  }
}

class CatModule extends AbstractModule {
  @Override
  protected void configure() {}

  @Provides
  Cat provideCat() {
    return new Cat();
  }
}

class Human {
  // private final Provider<Dog> dogProvider;
  // private final DogProvider dogProvider;
  private final Dog dog;

  @Inject
  Human(
      //Provider<Dog> dogProvider) {
      // DogProvider dogProvider) {
      Dog dog) {
    //this.dogProvider = dogProvider;
    this.dog = dog;
  }

  public String walkDog() {
    // return dogProvider.get().bark();
    return dog.bark();
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