package com.acme.sandbox;

import java.io.StringWriter;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.yaml.snakeyaml.Yaml;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/** Methods for turning objects into strings. */
public class Stringifier {
  public enum Format {
    JSON,
    YAML
  }

  /**
   * Marker annotation indicates that this object should be parsed recursively
   * by {@link Stringifier#stringify(Object).
   */
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface Stringifiable {

  }


  /** Prints an object in JSON format. */
  public String stringify(Object o) {
    return stringify(o, Format.JSON);
  }

  /**
   * Prints an object in some readable format. Mostly for unit testing but can
   * be used for any purpose.
   *
   * <p>Yaml format will parse the fields of any object any
   * annotated with {@link Stringifiable}.
   *
   * <p>The Json format will just whatever the Jackson library does.
   */
  public String stringify(Object o, Format format) {
    if (format == Format.YAML) {
      StringWriter writer = new StringWriter();
      new Yaml().dump(build(o), writer);
      return writer.toString();
    }

    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(o);
    } catch (Exception e) {
      throw new AssertionError("JSON deserialization error.", e);
    }
  }

  private ImmutableMap<String, Object> build(Object o) {
    ImmutableMap.Builder<String, Object> props = ImmutableMap.builder();
    Class<?> type = o == null ? null : o.getClass();
    if (o == null || !isStringifiable(type)) {
      return props.build();
    }

    List<Field> fields = sort(fields(type));
    for (Field field : fields) {
      Class<?> fieldType = field.getType();
      String fieldName = field.getName();
      Object fieldData = null;

      try {
        fieldData = field.get(o);
      } catch (IllegalArgumentException | IllegalAccessException e) {
        throw new AssertionError(
            String.format("Reflection error reading %s.%s.", type.getSimpleName(), fieldName),
            e);
      }

      if (fieldData == null) {
        continue;
      } else if (isStringifiable(field.getType())) {
        props.put(fieldName, build(fieldData));
      } else if (fieldType.isPrimitive()
          || fieldType.isAssignableFrom(Number.class)
          || fieldType.isAssignableFrom(Boolean.class)) {
        props.put(fieldName, fieldData);
      } else {
        props.put(fieldName, fieldData.toString());
      }
    }

    return props.build();
  }

  private Collection<Field> fields(Class<?> type) {
    Set<Field> fields = Sets.newHashSet();

    Class<?> superclass = type.getSuperclass();
    if (isStringifiable(superclass)) {
      fields.addAll(fields(superclass));
    }

    for (Field field : type.getDeclaredFields()) {
      field.setAccessible(true);
      int modifiers = field.getModifiers();
      if (!(Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers))) {
        fields.add(field);
      }
    }

    return fields;
  }

  private boolean isStringifiable(Class<?> type) {
    return type != null && type.getAnnotation(Stringifiable.class) != null;
  }

  private ImmutableList<Field> sort(Collection<Field> fields) {
    List<Field> list = Lists.newArrayList(fields);
    Collections.sort(list, new Comparator<Field>() {
      @Override
      public int compare(Field o1, Field o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });
    return ImmutableList.copyOf(list);
  }
}
