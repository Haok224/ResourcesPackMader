package org.haok.resourcespackmaker.log;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import org.haok.resourcespackmaker.App;
import org.haok.resourcespackmaker.io.StringPrintStream;
import org.haok.resourcespackmaker.util.Util;

public class Logger {
    private final SimpleDateFormat format;
    private PrintStream stream;

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
    public void info(Object o,boolean onlyConsole){
        System.out.println(o);
        if (onlyConsole){
            return;
        }
        stream.println(o);
    }

    public void warn(Throwable t) {
        stream.println(format.format(new Date().getTime()) + " [WARN] ");
        t.printStackTrace(stream);
        System.err.println(format.format(new Date().getTime()) + " [WARN] ");
        t.printStackTrace(System.err);
        Stage s = new Stage();
        s.setTitle("错误");
        StringPrintStream message = new StringPrintStream();
        t.printStackTrace(message);
        String err = message.toString();
        TextArea area = new TextArea(err);
        Scene scene = new Scene(area);
        s.setScene(scene);
        s.show();
    }

    public static class LogOutputStream extends OutputStream {
        @Override
        public void close() throws IOException {
            super.close();
            App.logger.info("Log output stream close now.",true);
        }

        @Override
        public void write(int b) {
            App.logger.info((char) b,true);
        }
    }
}
