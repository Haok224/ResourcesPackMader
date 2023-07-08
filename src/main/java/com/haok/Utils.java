package com.haok;

import javax.swing.*;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Utils {
    public static java.util.List<File> getSystemFonts() {
        FileFilter filter = (dir) -> dir.getAbsolutePath().endsWith(".ttf");
        List<File> files = new java.util.ArrayList<>(
                List.of(
                        Objects.requireNonNull(
                                new File("C:\\Windows\\Fonts\\")
                                        .listFiles(filter))));
        List<File> files2 = List.of(Objects.requireNonNull(new File("C:\\Users\\" + System.getenv().get("USERNAME") + "\\AppData\\Local\\Microsoft\\Windows\\Fonts").listFiles(filter)));
        files.addAll(files2);
        return files;
    }

    public static void exceptionHandle(Throwable t) {
        t.printStackTrace();
        StringWriter strWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(strWriter);
        t.printStackTrace(writer);
        String message = strWriter.toString();
        JOptionPane.showMessageDialog(Main.frame, "错误:\n" + message, Main.PROPERTIES.get("title").toString(), JOptionPane.ERROR_MESSAGE);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-d [hh:mm:ss]");
        String date_ = format.format(date);
        StringWriter causeWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(causeWriter);
        if (t.getCause() != null) {
            t.getCause().printStackTrace(pWriter);
        }else {
            pWriter.write("Null");
        }
        String cause = causeWriter.toString();
        String message_ = t.getMessage();
        File log = new File("resource-pack-maker.log");
        try {
            //noinspection ResultOfMethodCallIgnored
            log.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //get java information
        ProcessBuilder b = new ProcessBuilder("java","--version");
        Process p = null;
        try {
            p = b.start();
        } catch (IOException e) {
            System.exit(1);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder buf = new StringBuilder();
        try {
            buf.append(reader.readLine()).append("\n");
            buf.append(reader.readLine()).append("\n");
            buf.append(reader.readLine()).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            BufferedWriter logWriter = new BufferedWriter(new FileWriter(log,true));
            logWriter.write(String.format("Date:%s%nJava Version:%n%s%nCause:%n%s%nMassage:%n%s%nStackTrace:%n%s%n",date_, buf,cause,message_,message));
            logWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void copy(File source, File target) throws IOException {
        if (!target.exists()) {
            File file = target.getParentFile();
            file.mkdirs();
            target.createNewFile();
        }
        FileInputStream inputStream = new FileInputStream(source);
        FileOutputStream outputStream = new FileOutputStream(target);
        FileChannel input = inputStream.getChannel();
        FileChannel output = outputStream.getChannel();
        output.transferFrom(input, 0, input.size());
        inputStream.close();
        outputStream.close();
    }

    public static void write(File file, String content) throws IOException {
        System.out.println("Create target file:" + file.createNewFile());
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
        writer.flush();
        writer.close();
    }
}
