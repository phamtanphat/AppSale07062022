package com.example.appsale07062022.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.appsale07062022.common.AppConstant;

import java.util.Map;

public class AppCache {
    private static SharedPreferences sharedPreferences = null;
    private static SharedPreferences.Editor editor = null;
    private static AppCache instance;

    private AppCache(Context context) {
        sharedPreferences = context.getSharedPreferences(AppConstant.FILE_CACHE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static AppCache getInstance(Context context) {
        if (instance == null) {
            instance = new AppCache(context);
        }
        return instance;
    }

    /**
     * Save Data By Key
     *
     * @param key
     * @param value
     * @param <T>
     * @return <T>
     */
    public <T> AppCache setValue(String key, T value) {
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        return this;
    }

    /**
     * Execute Save Data
     */
    public void commit() {
        if (editor != null) {
            editor.commit();
        }
    }

    /**
     * Get Value If Exists
     *
     * @param key
     * @return object
     */
    public Object getValue(String key) {
        Map<String, ?> data = sharedPreferences.getAll();
        if (data.containsKey(key)) {
            return data.get(key);
        } else {
            return null;
        }
    }

    /**
     * Remove Data SharePreference
     */
    public void clear() {
        if (editor != null) {
            editor.clear();
            editor.commit();
        }
    }
}
