package com.sakila.data;
import java.lang.reflect.Field;
public final class Reflect {
    private Reflect() {}
    public static Object get(Object obj, String fieldName) {
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            return null;
        }
    }
}