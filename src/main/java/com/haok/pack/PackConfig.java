package com.haok.pack;

import com.haok.pack.data.type.ConfigDataType;
import com.haok.pack.data.type.FontDataType;
import com.haok.pack.data.type.MainMenuPictureDataType;

import java.util.HashMap;

public class PackConfig {
    HashMap<FontDataType, String> fontData = new HashMap<>();
    HashMap<MainMenuPictureDataType, String> mmpData = new HashMap<>();
    HashMap<ConfigDataType, String> configData = new HashMap<>();

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
}
