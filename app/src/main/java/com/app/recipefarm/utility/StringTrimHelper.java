package com.app.recipefarm.utility;

import java.lang.reflect.Field;

public class StringTrimHelper {

    /**
     * Trims all String fields of the given object.
     *
     * @param object The object to be trimmed.
     */
    public static void trim(Object object) {
        if (object == null) {
            return;
        }

        // Process all fields in the object, including private ones
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType().equals(String.class)) {
                    field.setAccessible(true); // Make private fields accessible
                    try {
                        String value = (String) field.get(object);
                        if (value != null) {
                            field.set(object, value.trim());
                        }
                    } catch (IllegalAccessException e) {
                        System.err.println("Error trimming field: " + field.getName());
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}