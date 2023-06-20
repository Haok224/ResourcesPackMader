package com.haok.pack;

import com.haok.pack.data.type.SaveConfigDataType;

import java.io.File;
import java.io.IOException;

public class PackMaker {
    public static void make(PackConfig config) throws IOException {
        File packPath = new File(config.get(SaveConfigDataType.PACK_PATH));
        System.out.println("Make pack dir:"+packPath.mkdirs());
        File pack_mcmeta = new File(packPath,"pack.mcmeta");
        System.out.println("Make pack.mcmeta:"+pack_mcmeta.createNewFile());
    }
}
