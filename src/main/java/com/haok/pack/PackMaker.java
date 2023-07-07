package com.haok.pack;

import com.haok.Main;
import com.haok.Utils;
import com.haok.pack.data.type.ConfigDataType;
import com.haok.pack.data.type.FontDataType;
import com.haok.pack.data.type.PanoramaDataType;
import com.haok.pack.data.type.SaveConfigDataType;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class PackMaker {
    public static void make(PackConfig config) throws IOException {
        //if pack name is null -> show message dialog and focus on pack name text field
        if (config.get(ConfigDataType.PACK_NAME) == null) {
            JOptionPane.showMessageDialog(Main.frame, "资源包名不能为空", Main.PROPERTIES.get("title").toString(), JOptionPane.INFORMATION_MESSAGE);
            Main.pane.setSelectedIndex(0);
            Main.packName.getTextField().requestFocusInWindow();
            return;
        }
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
                        """, config.get(ConfigDataType.VERSION),
                config.get(ConfigDataType.DESCRIBE) == null ? "" : config.get(ConfigDataType.DESCRIBE));
        System.out.println("Write pack.mcmeta:\n" + pack_meta);
        Utils.write(pack_mcmeta, pack_meta);
        //font
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
            Utils.copy(new File(config.get(FontDataType.FILE_PATH)), font);
            fontJSON = new File(fontPath, "default.json");
            Utils.write(fontJSON, String.format("""
                    {
                        "providers": [
                            {
                                "type": "ttf",
                                "file": "minecraft:%s.ttf",
                                "shift": [0, 1],
                                "size": 11.0,
                                "oversample": 4.0
                            }
                        ]
                    }
                    """, config.get(FontDataType.NAME)));
        }
        {
            String front = config.get(PanoramaDataType.PANORAMA_0);
            String behind = config.get(PanoramaDataType.PANORAMA_1);
            String up = config.get(PanoramaDataType.PANORAMA_2);
            String down = config.get(PanoramaDataType.PANORAMA_3);
            String left = config.get(PanoramaDataType.PANORAMA_4);
            String right = config.get(PanoramaDataType.PANORAMA_5);
            if (front != null
                    || behind != null
                    || up != null
                    || down != null
                    || left != null
                    || right != null
            ) {
                File background = new File(minecraft, "/textures/gui/title/background");
                System.out.println("Make dir:assets/minecraft/textures/gui/title/background" + background.mkdirs());
                if (front != null) {
                    File panorama_0 = new File(background, "panorama_0.png");
                    Utils.copy(new File(front), panorama_0);
                }
                if (right != null) {
                    File panorama_1 = new File(background, "panorama_1.png");
                    Utils.copy(new File(right), panorama_1);
                }
                if (behind != null) {
                    File panorama_2 = new File(background, "panorama_2.png");
                    Utils.copy(new File(behind), panorama_2);
                }
                if (left != null){
                    File panorama_3 = new File(background,"panorama_3");
                    Utils.copy(new File(left),panorama_3);
                }
            }
        }
    }
}
