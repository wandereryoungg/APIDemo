package com.young.myaddemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.rbt.vrde.core.CoreManager;

import java.util.Map;
import java.util.Set;

public class CryptPrefs implements SharedPreferences {

    private static final String TAG = CryptPrefs.class.getSimpleName();
    SharedPreferences mPrefs = null;

    public CryptPrefs(Context context, String name, int mode) {
        if (context == null) {
            context = CoreManager.getContext();
        }
        mPrefs = context.getSharedPreferences(name, mode);
    }

    public static CryptPrefs getPrefs(Context context, String name, int mode) {
        return new CryptPrefs(context, name, mode);
    }

    @Override
    public Map<String, ?> getAll() {
        return mPrefs.getAll();
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        String value = mPrefs.getString(key, null);
        if (value == null) {
            value = defValue;
        }
        return value;
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        Set<String> values = mPrefs.getStringSet(key, defValues);
        if (values == null) {
            return defValues;
        }
        for (String str : values) {
            String value = null;
            if (str != null) {
                value = str;
                values.remove(str);
                values.add(value);
            }
        }
        return values;
    }

    @Override
    public int getInt(String key, int defValue) {
        return mPrefs.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return mPrefs.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return mPrefs.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return mPrefs.getBoolean(key, defValue);
    }

    @Override
    public boolean contains(String key) {
        return mPrefs.contains(key);
    }

    @Override
    public Editor edit() {
        return new MyEditorImpl(mPrefs.edit());
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }

    public class MyEditorImpl implements Editor {

        Editor editor = null;

        public MyEditorImpl(Editor editor) {
            this.editor = editor;
        }

        @Override
        public Editor putString(String key, @Nullable String value) {
            editor.putString(key, value);
            return this;
        }

        @Override
        public Editor putStringSet(String key, @Nullable Set<String> values) {
            editor.putStringSet(key, values);
            return this;
        }

        @Override
        public Editor putInt(String key, int value) {
            editor.putInt(key, value);
            return this;
        }

        @Override
        public Editor putLong(String key, long value) {
            editor.putLong(key, value);
            return this;
        }

        @Override
        public Editor putFloat(String key, float value) {
            editor.putFloat(key, value);
            return this;
        }

        @Override
        public Editor putBoolean(String key, boolean value) {
            editor.putBoolean(key, value);
            return this;
        }

        @Override
        public Editor remove(String key) {
            editor.remove(key);
            return this;
        }

        @Override
        public Editor clear() {
            editor.clear();
            return this;
        }

        @Override
        public boolean commit() {
            return editor.commit();
        }

        @Override
        public void apply() {
            editor.apply();
        }
    }
}
