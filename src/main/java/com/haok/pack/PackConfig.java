package com.haok.pack;

import com.haok.pack.data.type.*;

import java.util.HashMap;

public class PackConfig {
    final HashMap<FontDataType, String> fontData = new HashMap<>();
    final HashMap<PanoramaDataType, String> mmpData = new HashMap<>();
    final HashMap<ConfigDataType, String> configData = new HashMap<>();
    final HashMap<SaveConfigDataType, String> saveConfigData = new HashMap<>();
    final HashMap<CustomLoadingBackgroundDataType, String> customLoadingBackgroundData = new HashMap<>();

    public void put(FontDataType key, String value) {
        fontData.put(key, value);
        System.out.println("Key is:" + key + "\nValue is:" + value);
    }

    public void put(PanoramaDataType key, String value) {
        mmpData.put(key, value);
        System.out.println("Key is:" + key + "\nValue is:" + value);
    }

    public void put(ConfigDataType key, String value) {
        configData.put(key, value);
        System.out.println("Key is:" + key + "\nValue is:" + value);
    }

    public void put(SaveConfigDataType key, String value) {
        saveConfigData.put(key, value);
        System.out.println("Key is:" + key + "\nValue is:" + value);
    }

    public void put(CustomLoadingBackgroundDataType key, String value) {
        customLoadingBackgroundData.put(key, value);
        System.out.println("Key is:" + key + "\nValue is:" + value);
    }

    public String get(FontDataType key) {
        return fontData.get(key);
    }

    public String get(PanoramaDataType key) {
        return mmpData.get(key);
    }

    public String get(ConfigDataType key) {
        return configData.get(key);
    }

    public String get(SaveConfigDataType key) {
        return saveConfigData.get(key);
    }
    public String get(CustomLoadingBackgroundDataType key) {
        return customLoadingBackgroundData.get(key);
    }
}
