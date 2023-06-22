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
        JOptionPane.showMessageDialog(Main.frame, "错误:\n" + message, FileFilters.TITLE, JOptionPane.ERROR_MESSAGE);
    }
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void copy(File source, File target) throws IOException {
        if (!target.exists()){
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
    public static void write(File file,String content) throws  IOException{
        System.out.println("Create target file:"+file.createNewFile());
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
        writer.flush();
        writer.close();
    }
}
