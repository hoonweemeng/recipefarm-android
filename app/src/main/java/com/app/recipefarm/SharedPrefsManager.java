package com.app.recipefarm;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SharedPrefsManager {
    private static final String PREFS_NAME = "AppPreferences";
    private static SharedPrefsManager instance;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final Gson gson;

    // Private constructor
    private SharedPrefsManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    // Singleton instance
    public static synchronized SharedPrefsManager shared(Context context) {
        if (instance == null) {
            instance = new SharedPrefsManager(context.getApplicationContext());
        }
        return instance;
    }

    // Save any type of data
    public <T> void saveData(String key, T value) {
        if (value == null) {
            editor.remove(key).apply();
            return;
        }

        if (value instanceof String || value instanceof Integer || value instanceof Boolean
                || value instanceof Float || value instanceof Long) {
            // Save primitive data types directly
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            }
        } else {
            // Serialize and save objects as JSON
            String json = gson.toJson(value);
            editor.putString(key, json);
        }
        editor.apply();
    }

    // Retrieve data
    public <T> T getData(String key, Class<T> type) {
        if (type == String.class) {
            return type.cast(sharedPreferences.getString(key, null));
        } else if (type == Integer.class) {
            return type.cast(sharedPreferences.getInt(key, 0));
        } else if (type == Boolean.class) {
            return type.cast(sharedPreferences.getBoolean(key, false));
        } else if (type == Float.class) {
            return type.cast(sharedPreferences.getFloat(key, 0f));
        } else if (type == Long.class) {
            return type.cast(sharedPreferences.getLong(key, 0L));
        } else {
            // Deserialize JSON for complex objects
            String json = sharedPreferences.getString(key, null);
            return json == null ? null : gson.fromJson(json, type);
        }
    }

    // Retrieve a list or complex object
    public <T> T getData(String key, Type typeOfT) {
        String json = sharedPreferences.getString(key, null);
        return json == null ? null : gson.fromJson(json, typeOfT);
    }

    // Remove a specific key
    public void removeKey(String key) {
        editor.remove(key).apply();
    }

    // Clear all preferences
    public void clearAll() {
        editor.clear().apply();
    }
}
