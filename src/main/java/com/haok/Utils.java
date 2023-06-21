package com.haok;

import javax.swing.*;
import java.io.*;
import java.nio.channels.FileChannel;
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
        JOptionPane.showMessageDialog(Main.frame, "错误:\n" + message, Config.TITLE, JOptionPane.ERROR_MESSAGE);
    }

    public static void copy(File source, File target) throws IOException {
        FileInputStream inputStream = new FileInputStream(source);
        FileOutputStream outputStream = new FileOutputStream(target);
        FileChannel input = inputStream.getChannel();
        FileChannel output = outputStream.getChannel();
        output.transferFrom(input, 0, input.size());
        inputStream.close();
        outputStream.close();
    }
}
