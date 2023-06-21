package com.haok.pack;

import com.haok.Utils;
import com.haok.pack.data.type.ConfigDataType;
import com.haok.pack.data.type.FontDataType;
import com.haok.pack.data.type.SaveConfigDataType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PackMaker {
    public static void make(PackConfig config) throws IOException {
        //pack.mcmeta
        File packPath = new File(config.get(SaveConfigDataType.PACK_PATH), config.get(ConfigDataType.PACK_NAME));
        System.out.println("Make pack dir:" + packPath.mkdirs());
        File pack_mcmeta = new File(packPath, "pack.mcmeta");
        System.out.println("Make pack.mcmeta:" + pack_mcmeta.createNewFile());
        String pack_meta = String.format("""
                {
                   "pack":{
                      "pack_format":%s,
                      "description":"%s"
                   }
                }
                """, config.get(ConfigDataType.VERSION), config.get(ConfigDataType.DESCRIBE) == null ? "" : config.get(ConfigDataType.DESCRIBE));
        //font
        System.out.println("Write pack.mcmeta:\n" + pack_meta);
        BufferedWriter pack_mcmeta_writer = new BufferedWriter(new FileWriter(pack_mcmeta));
        pack_mcmeta_writer.write(pack_meta);
        pack_mcmeta_writer.flush();
        pack_mcmeta_writer.close();
        File assets = new File(packPath, "assets");
        File minecraft = new File(assets, "minecraft");
        System.out.println("Make assets dir:" + assets.mkdirs());
        System.out.println("Make 'minecraft' child file:" + minecraft.mkdirs());
        File font;
        File fontJSON;
        File fontPath;
        if (config.get(FontDataType.NAME) != null && config.get(FontDataType.FILE_PATH) != null) {
            fontPath = new File(minecraft, "font");
            System.out.println("Make font dir:" + fontPath.mkdirs());
            font = new File(fontPath, config.get(FontDataType.NAME) + ".ttf");
            System.out.println("Make font file:" + font.createNewFile());
            Utils.copy(new File(config.get(FontDataType.FILE_PATH)), font);
            fontJSON = new File(fontPath, "default.json");
            BufferedWriter fontJSONWriter = new BufferedWriter(new FileWriter(fontJSON));
            fontJSONWriter.write(String.format("""
                    {
                        "providers": [
                            {
                                "type": "ttf",
                                "file": "minecraft:%s.ttf",
                    \t\t\t"shift": [0, 1],
                     \t\t\t"size": 11.0,
                     \t\t\t"oversample": 4.0
                            }
                        ]
                    }
                    """, config.get(FontDataType.NAME)));
            fontJSONWriter.flush();
            fontJSONWriter.close();
        }
    }
}
