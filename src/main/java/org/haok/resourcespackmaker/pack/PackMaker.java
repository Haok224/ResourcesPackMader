package org.haok.resourcespackmaker.pack;

import org.haok.resourcespackmaker.App;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.haok.resourcespackmaker.pack.PackConfig.*;
import static org.haok.resourcespackmaker.util.Util.*;

public class PackMaker {
    static File icon = null;

    public static void makePack(boolean isZip) throws Exception {
        if (success) {
            success = false;
        }
        File packPath = new File(exportPath.getAbsolutePath() + App.SEPARATOR + packName);
        App.logger.info("create pack directory:" + packPath.mkdirs());  //创建目录
        File pack_mcmeta = new File(packPath.getAbsolutePath() + App.SEPARATOR + "pack.mcmeta");    //pack.mcmeta
        App.logger.info("make pack.mcmeta:" + pack_mcmeta.createNewFile());     //创建pack.mcmeta文件
        BufferedWriter writer = new BufferedWriter(new FileWriter(pack_mcmeta));
        {
            String s = String.format("""
                    {
                       "pack":{
                          "pack_format":%d,
                          "description":"%s"
                       }
                    }
                                        
                    """, packVersion, packIntroduction);
            writer.write(s);
        }
        
        writer.close();
        if (!(ttfFile == null)) {
            File fontPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "font");
            App.logger.info("create font directory:" + fontPath.mkdirs());
            File fontFile = new File(fontPath.getAbsolutePath() + App.SEPARATOR + "font.ttf");
            copy(ttfFile, fontFile);
            File jsonFile = new File(fontPath + App.SEPARATOR + "default.json");
            App.logger.info("make default.json file:" + jsonFile.createNewFile());
            BufferedWriter jsonWriter = new BufferedWriter(new FileWriter(jsonFile));
            {
                String s = """
                        {
                            "providers": [
                                {
                                    "type": "ttf",
                                    "file": "minecraft:font",
                        			"shift": [0, 1],
                         			"size": 11.0,
                         			"oversample": 4.0
                                }
                            ]
                        }
                                                
                        """;
                jsonWriter.write(s);
            }
            
            jsonWriter.close();

            if (!(iconFile == null)) {
                icon = new File(packPath.getAbsolutePath() + App.SEPARATOR + "pack.png");
                copy(iconFile, icon);
                
            }
            makePanorama(packPath);
            makeLoadBackground(packPath);
            successFile = packPath;
            if (isZip) {
                File zipFile = new File(exportPath.toString() + App.SEPARATOR + packName + ".zip");
                App.logger.info("create zip file:" + zipFile.createNewFile());
                ArrayList<File> fileArrayList = new ArrayList<>();
                fileArrayList.add(new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets"));
                fileArrayList.add(pack_mcmeta);
                if (!(iconFile == null)) {
                    fileArrayList.add(icon);
                }
                toZip(zipFile.getAbsolutePath(), fileArrayList);
                successFile = zipFile;
                deleteDir(packPath);
                
            }
        }
        success = true;
    }

    private static void makeLoadBackground(File packPath) throws IOException {
        if (loadBackground0 != null) {
            File loadBackGroundPath = new File(packPath.getAbsolutePath() + "" + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "optifine" + App.SEPARATOR + "gui" + App.SEPARATOR + "loading");
            App.logger.info("create load background directory:" + loadBackGroundPath.mkdirs());
            if (!isGrid) {
                File file = new File(loadBackGroundPath.getAbsolutePath() + App.SEPARATOR + "loading.properties");
                App.logger.info("create loading.properties:" + file.createNewFile());
                BufferedWriter writer1 = new BufferedWriter(new FileWriter(file));
                writer1.write("scaleMode=stretch");
            }
            File dest = new File(loadBackGroundPath.getAbsolutePath() + App.SEPARATOR + "background0.png");
            App.logger.info("make background-0 file:" + dest.createNewFile());
            copy(loadBackground0, dest);
        }
        
        if (loadBackground_1 != null) {
            File loadBackGroundPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "optifine" + App.SEPARATOR + "gui" + App.SEPARATOR + "loading");
            App.logger.info("create load background directory:" + loadBackGroundPath.mkdirs());
            if (!isGrid) {
                File file = new File(loadBackGroundPath.getAbsolutePath() + App.SEPARATOR + "loading.properties");
                App.logger.info("create loading.properties:" + file.createNewFile());
                BufferedWriter writer1 = new BufferedWriter(new FileWriter(file));
                writer1.write("scaleMode=stretch");
            }
            
            File dest = new File(loadBackGroundPath.getAbsolutePath() + App.SEPARATOR + "background-1.png");
            App.logger.info("make background-1 file:" + dest.createNewFile());
            copy(loadBackground0, dest);
        }
        
        if (loadBackground1 != null) {
            File loadBackGroundPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "optifine" + App.SEPARATOR + "gui" + App.SEPARATOR + "loading");
            App.logger.info("create load background directory:" + loadBackGroundPath.mkdirs());
            if (!isGrid) {
                File file = new File(loadBackGroundPath.getAbsolutePath() + App.SEPARATOR + "loading.properties");
                App.logger.info("create loading.properties:" + file.createNewFile());
                BufferedWriter writer1 = new BufferedWriter(new FileWriter(file));
                writer1.write("scaleMode=stretch");
            }
            
            File dest = new File(loadBackGroundPath.getAbsolutePath() + App.SEPARATOR + "background1.png");
            App.logger.info("make background1 file:" + dest.createNewFile());
            copy(loadBackground0, dest);
        }
        
    }

    private static void makePanorama(File packPath) throws IOException {
        if (panorama0 != null) {
            File panoramaPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "textures" + App.SEPARATOR + "gui" + App.SEPARATOR + "title" + App.SEPARATOR + "background");
            App.logger.info("make panorama directory:" + panoramaPath.mkdirs());
            File panorama_0 = new File(panoramaPath.getAbsolutePath() + "panorama_0.png");
            copy(panorama0, panorama_0);
        }
        
        if (panorama1 != null) {
            File panoramaPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "textures" + App.SEPARATOR + "gui" + App.SEPARATOR + "title" + App.SEPARATOR + "background");
            App.logger.info("make panorama directory:" + panoramaPath.mkdirs());
            File panorama_1 = new File(panoramaPath.getAbsolutePath() + "panorama_1.png");
            copy(panorama0, panorama_1);
        }
        
        if (panorama2 != null) {
            File panoramaPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "textures" + App.SEPARATOR + "gui" + App.SEPARATOR + "title" + App.SEPARATOR + "background");
            App.logger.info("make panorama directory:" + panoramaPath.mkdirs());
            File panorama_2 = new File(panoramaPath.getAbsolutePath() + "panorama_2.png");
            copy(panorama0, panorama_2);
        }
        
        if (panorama3 != null) {
            File panoramaPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "textures" + App.SEPARATOR + "gui" + App.SEPARATOR + "title" + App.SEPARATOR + "background");
            App.logger.info("make panorama directory:" + panoramaPath.mkdirs());
            File panorama_3 = new File(panoramaPath.getAbsolutePath() + "panorama_3.png");
            copy(panorama0, panorama_3);
        }
        
        if (panorama4 != null) {
            File panoramaPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "textures" + App.SEPARATOR + "gui" + App.SEPARATOR + "title" + App.SEPARATOR + "background");
            File panorama_4 = new File(panoramaPath.getAbsolutePath() + "panorama_4.png");
            copy(panorama0, panorama_4);
        }
        
        if (panorama5 != null) {
            File panoramaPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "textures" + App.SEPARATOR + "gui" + App.SEPARATOR + "title" + App.SEPARATOR + "background");
            App.logger.info("make panorama directory:" + panoramaPath.mkdirs());
            File panorama_5 = new File(panoramaPath.getAbsolutePath() + "panorama_5.png");
            copy(panorama0, panorama_5);
        }
        
        if (background != null) {
            File panoramaPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "textures" + App.SEPARATOR + "gui" + App.SEPARATOR + "title" + App.SEPARATOR + "background");
            App.logger.info("make panorama directory:" + panoramaPath.mkdirs());
            File panorama_overlay = new File(panoramaPath.getAbsolutePath() + App.SEPARATOR + "panorama_overlay.png");
            copy(background, panorama_overlay);
        }
        
    }
}
