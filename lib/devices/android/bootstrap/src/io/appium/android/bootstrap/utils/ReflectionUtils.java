package io.appium.android.bootstrap.utils;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionUtils {

  public static Object getField(String field, Object object) {
    return getField(object.getClass(), field, object);
  }

  public static Object getField(String className, String field, Object object) {
    return getField(getClass(className), field, object);
  }

  public static Object getField(Class clazz, String fieldName, Object object) {
    try {
      final Field field = clazz.getDeclaredField(fieldName);
      field.setAccessible(true);

      return field.get(object);
    } catch (Exception e) {
      String msg = String.format("error while getting field %s from object %s", fieldName, object);
      Log.e("APPIUM", msg, e);
      throw new RuntimeException(msg, e);
    }
  }

  public static Method method(String className, String method, Class... parameterTypes) {
    return method(getClass(className), method, parameterTypes);
  }

  public static Method method(Class clazz, String methodName, Class... parameterTypes) {
    try {
      Method method = clazz.getDeclaredMethod(methodName, parameterTypes);

      return method;
    } catch (Exception e) {
      String msg = String.format("error while getting method %s from class %s with parameter types %s", methodName, clazz, Arrays.toString(parameterTypes));
      Log.e("APPIUM", msg, e);
      throw new RuntimeException(msg, e);
    }
  }

  public static Object invoke(Method method, Object object, Object... parameters) {
    try {
      method.setAccessible(true);

      return method.invoke(object, parameters);
    } catch (Exception e) {
      String msg = String.format("error while invoking method %s on object %s with parameters %s", method, object, Arrays.toString(parameters));
      Log.e("APPIUM", msg, e);
      throw new RuntimeException(msg, e);
    }
  }

  public static Class getClass(String name) {
    try {
      return Class.forName(name);
    } catch (ClassNotFoundException e) {
      String msg = String.format("unable to find class %s", name);
      Log.e("APPIUM", msg, e);
      throw new RuntimeException(msg, e);
    }
  }
}
