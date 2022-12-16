package com.github.silverspecter27.commandbuilder.CommandBuilder.Register.Utils;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    @NotNull
    public static Set<Field> getFields(Class<?> clazz) throws IllegalArgumentException {
        return getFields(true, clazz);
    }

    @NotNull
    public static Set<Field> getFields(boolean b, @NotNull Class<?> clazz) throws IllegalArgumentException {

        final Set<Field> fields = new HashSet<>();

        Field[] publicFields = clazz.getFields();
        Field[] privateFields = clazz.getDeclaredFields();

        if (b) {
            for (Field field : publicFields) {
                field.setAccessible(true);
            }

            for (Field field : privateFields) {
                field.setAccessible(true);
            }
        }

        Collections.addAll(fields, publicFields);
        Collections.addAll(fields, privateFields);

        return fields;
    }

    @NotNull
    public static Set<Object> getObjects(@NotNull Object clazz) {
        return getObjects(true, clazz);
    }

    @NotNull
    public static Set<Object> getObjects(boolean b, @NotNull Object clazz) {
        Set<Object> objects = new HashSet<>();
        for (final Field field : getFields(b, clazz.getClass())) {
            try {
                objects.add(field.get(clazz));
            } catch (IllegalAccessException ignore) {
            }
        }
        return objects;
    }

    @NotNull
    public static <A extends Annotation> Map<Object, A> getObjectWithAnnotation(@NotNull Object clazz, Class<A> annotation) {
        return getObjectWithAnnotation(true, clazz, annotation);
    }

    @NotNull
    public static <A extends Annotation> Map<Object, A> getObjectWithAnnotation(boolean b, @NotNull Object clazz, Class<A> annotation) {
        Map<Object, A> objects = new HashMap<>();
        for (final Field field : getFields(b, clazz.getClass())) {
            final A a = field.getAnnotation(annotation);
            if (a == null) {
                continue;
            }
            Object object = null;
            try {
                object = field.get(clazz);
                objects.put(object, a);
            } catch (IllegalAccessException ignore) {
            }
        }
        return objects;
    }

    @NotNull
    public static <T> Set<T> getObjects(Object clazz, @NotNull Class<T> instance) {
        return getObjects(true, clazz, instance);
    }

    @NotNull
    public static <T> Set<T> getObjects(boolean b, Object clazz, @NotNull Class<T> instance) {
        Set<T> set = new HashSet<>();
        for (Object object : getObjects(clazz)) {
            try {
                set.add(instance.cast(object));
            } catch (ClassCastException ignore) {
            }
        }
        return set;
    }

    @NotNull
    public static <T, A extends Annotation> Map<T, A> getObjectWithAnnotation(@NotNull Object clazz, Class<T> type, Class<A> annotation) {
        return getObjectWithAnnotation(true, clazz, type, annotation);
    }

    @NotNull
    public static <T, A extends Annotation> Map<T, A> getObjectWithAnnotation(boolean b, @NotNull Object clazz, Class<T> type, Class<A> annotation) {
        Map<T, A> objects = new HashMap<>();
        for (final Field field : getFields(b, clazz.getClass())) {
            final A a = field.getAnnotation(annotation);
            if (a == null) {
                continue;
            }
            Object object = null;
            try {
                object = field.get(clazz);
            } catch (IllegalAccessException ignore) {
                continue;
            }
            try {
                objects.put(type.cast(object), a);
            } catch (ClassCastException ignore) {
            }
        }
        return objects;
    }

    @NotNull
    public static Set<Object> getObjectsByType(@NotNull Object clazz, Class<?>... instances) {
        return getObjectsByType(true, clazz, instances);
    }

    @NotNull
    public static Set<Object> getObjectsByType(boolean b, @NotNull Object clazz, Class<?>... instances) {
        Set<Object> objects = new HashSet<>();

        for (Object object : getObjects(b, clazz.getClass())) {
            for (final Class<?> instance : instances) {
                if (instance == null) {
                    continue;
                }
                try {
                    objects.add(instance.cast(object));
                } catch (ClassCastException e) {
                    continue;
                }
                break;
            }
        }
        return objects;
    }

    @NotNull
    public static <A extends Annotation> Map<Object, A> getObjectsByTypeWithAnnotation(@NotNull Object clazz, @NotNull Class<A> annotation, Class<?>... instances) {
        return getObjectsByTypeWithAnnotation(true, clazz, annotation, instances);
    }

    @NotNull
    public static <A extends Annotation> Map<Object, A> getObjectsByTypeWithAnnotation(boolean b, @NotNull Object clazz, @NotNull Class<A> annotation, Class<?>... instances) {
        Map<Object, A> objects = new HashMap<>();

        for (final Field field : getFields(b, clazz.getClass())) {
            final A a = field.getAnnotation(annotation);
            if (a == null) {
                continue;
            }
            Object object;
            try {
                object = field.get(clazz);
            } catch (IllegalAccessException e) {
                continue;
            }
            if (instances.length == 0) {
                objects.put(object, a);
                continue;
            }
            for (Class<?> type : instances) {
                if (type == null) {
                    continue;
                }
                try {
                    type.cast(object);
                } catch (ClassCastException e) {
                    continue;
                }
                objects.put(object, a);
                break;
            }
        }
        return objects;
    }
}
