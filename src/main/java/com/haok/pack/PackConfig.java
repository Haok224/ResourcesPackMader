package com.haok.pack;

import com.haok.pack.data.type.ConfigDataType;
import com.haok.pack.data.type.FontDataType;
import com.haok.pack.data.type.MainMenuPictureDataType;
import com.haok.pack.data.type.SaveConfigDataType;

import java.util.HashMap;

public class PackConfig {
    final HashMap<FontDataType, String> fontData = new HashMap<>();
    final HashMap<MainMenuPictureDataType, String> mmpData = new HashMap<>();
    final HashMap<ConfigDataType, String> configData = new HashMap<>();
    final HashMap<SaveConfigDataType, String> saveConfigData = new HashMap<>();

    public void put(FontDataType key, String value) {
        fontData.put(key, value);
        System.out.println("Key is:" + key + "\nValue is:" + value);
    }

    public void put(MainMenuPictureDataType key, String value) {
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

    public String get(FontDataType key) {
        return fontData.get(key);
    }

    public String get(MainMenuPictureDataType key) {
        return mmpData.get(key);
    }

    public String get(ConfigDataType key) {
        return configData.get(key);
    }

    public String get(SaveConfigDataType key) {
        return saveConfigData.get(key);
    }
}
