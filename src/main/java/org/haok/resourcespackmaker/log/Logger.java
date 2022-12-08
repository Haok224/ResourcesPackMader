package org.haok.resourcespackmaker.log;

import org.haok.resourcespackmaker.util.Util;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Logger {
    private PrintStream stream;
    private final SimpleDateFormat format;

    public Logger() {
        this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        try {
            File path = new File("log");
            System.out.println("make log dir:" + path.mkdirs());
            File file = new File("log/" + new SimpleDateFormat("yyyy-MM-dd_HH：mm：ss：SS").format(new Date()) + ".log");
            System.out.println("create log file:" + file.createNewFile());
            stream = new PrintStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            File[] fileList = new File("log/").listFiles();
            ArrayList<File> logFileList = new ArrayList<>();
            ArrayList<File> zipFileList = new ArrayList<>();
            int logFile = 0;
            int zipFile = 0;
            if (fileList != null) {
                for (File file : fileList) {
                    String path = file.getPath();
                    if (Util.getFileType(path).equals("log")) {
                        logFile++;
                        logFileList.add(file);
                    } else if (Util.getFileType(path).equals("zip")) {
                        zipFile++;
                        zipFileList.add(file);
                    }
                }
            }
            System.out.println("log file amount:" + logFile);
            if (logFile >= 10) {
                System.out.println("log file amount >= 10.create zip");
                Util.toZip("log/" + new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss_SS").format(new Date()) + ".zip", logFileList);
                for (File file : logFileList) {
                    System.out.println("delete log file:" + file.delete());
                }
            }
            if (zipFile >= 5) {
                for (File file : fileList) {
                    System.out.println("delete file:" + file.delete());
                }
            }
        }).start();
    }

    public void info(Object o) {
        stream.println(format.format(new Date().getTime()) + " [INFO] " + o);
        System.out.println(format.format(new Date().getTime()) + " [INFO] " + o);
    }

    public void warn(Throwable t) {
        stream.println(format.format(new Date().getTime()) + " [WARN] ");
        t.printStackTrace(stream);
        System.err.println(format.format(new Date().getTime()) + " [WARN] ");
        t.printStackTrace(System.err);
    }
}
