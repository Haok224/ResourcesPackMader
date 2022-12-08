package org.haok.resourcespackmaker.pack;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.haok.resourcespackmaker.App;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

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
            JSONObject object = new JSONObject(new HashMap<>());
            object.put("pack", new Pack(packVersion, packIntroduction));
            String json = object.toJSONString();
            writer.write(json);
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
                HashMap<String,Object> set = new HashMap<>();
                HashMap<String,Object> map = new HashMap<>();
                map.put("type","ttf");
                map.put("file","minecraft:font.ttf");
                map.put("shift",new int[]{0,1});
                map.put("size",11.0);
                map.put("oversample",4.0);
                HashMap[] maps = new HashMap[]{map};
                set.put("providers",maps);
                String json = JSON.toJSONString(set);
                App.logger.info("default json file content:\n"+json);
                jsonWriter.write(json);
            }
            jsonWriter.close();

            if (!(iconFile == null)) {
                icon = new File(packPath.getAbsolutePath() + App.SEPARATOR + "pack.png");
                copy(iconFile, icon);
            }
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
            if (loadBackground0 != null){
                //todo
                System.out.println("todo");
            }
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
}
