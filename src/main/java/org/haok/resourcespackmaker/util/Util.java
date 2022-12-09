package org.haok.resourcespackmaker.util;

import org.haok.resourcespackmaker.App;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Util {
    private static final byte[] buf = new byte[1024 * 1024];

    public static void copy(File source, File dest) throws IOException {
        if (!dest.isFile()) {
            App.logger.info("dest file is not a file. create new file:" + dest.createNewFile());
        }
        App.logger.info("copy file from:" + source + " to: " + dest);
        try (InputStream input = new FileInputStream(source); OutputStream output = new FileOutputStream(dest)) {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        }
    }

    public static boolean deleteDir(File directory) {
        App.logger.info("delete directory:" + directory);
        if (directory.isDirectory()) {
            String[] children = directory.list();
            for (int i = 0; i < Objects.requireNonNull(children).length; i++) {
                boolean success = deleteDir(new File(directory, children[i]));

                if (!success) {
                    return false;
                }
            }
        }
        return directory.delete();
    }

    public static String getFileType(String fileName) {
        String[] strArray = fileName.split("\\.");
        int suffixIndex = strArray.length - 1;
        return strArray[suffixIndex];
    }

    public static void toZip(String zipFileName, List<File> srcFiles) {
        ZipOutputStream zos = null;
        App.logger.info("to zip scr files:" + srcFiles);
        try {
            File file = new File(zipFileName);
            if (!file.isFile()) {
                App.logger.info("create zip file:" + file.createNewFile());
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            zos = new ZipOutputStream(fileOutputStream);
            for (File srcFile : srcFiles) {
                compress(srcFile, zos, srcFile.getName(), true);
            }
        } catch (Exception e) {
            throw new RuntimeException("zip error from Util", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception {

        if (sourceFile.isFile()) {
            zos.putNextEntry(new ZipEntry(name));
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                if (KeepDirStructure) {
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    if (KeepDirStructure) {
                        compress(file, zos, name + "/" + file.getName(), true);
                    } else {
                        compress(file, zos, file.getName(), false);
                    }
                }
            }
        }
    }
}
