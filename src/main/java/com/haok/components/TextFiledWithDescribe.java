package com.haok.components;

import com.haok.Main;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public class TextFiledWithDescribe extends JPanel {
    private final JTextField textField;

    private final JFileChooser chooser;
    private final JLabel describe;
    private final JButton remove;
    private String text;
    private SelectFileListener listener;
    private RemoveFileListener removeFileListener;
    private boolean isShowSaveDialog = false;

    public TextFiledWithDescribe(String describe, boolean hasButton, boolean editable) {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(300, 50));
        //label settings
        this.describe = new JLabel(describe);
        //text filed settings
        textField = new JTextField(1);
        textField.setSize(200, 25);
        textField.setEditable(editable);
        //remove button
        remove = new JButton("×");
        remove.setVisible(false);
        remove.addActionListener(e -> {
            textField.setText("");
            remove.setVisible(false);
            if (removeFileListener != null) {
                removeFileListener.onRemoveFile();
            }
            System.out.println(this + "\nremove file.");
        });
        //browse button
        JButton open = new JButton("浏览");
        chooser = new JFileChooser();
        ActionListener actionListener = e -> {
            int target;
            if (isShowSaveDialog) {
                target = chooser.showSaveDialog(Main.frame);
            } else {
                target = chooser.showOpenDialog(Main.frame);
            }
            if (target == JFileChooser.APPROVE_OPTION) {
                File file;
                file = chooser.getSelectedFile();
                text = Objects.requireNonNull(file).getAbsolutePath();
                textField.setText(file.getAbsolutePath());
                remove.setVisible(true);
                try {
                    if (listener != null) {
                        listener.onFileSelect(file);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println(this + "choose a file:\n" + file.getAbsolutePath());
            }
        };
        open.addActionListener(actionListener);
        //add component
        add(this.describe, BorderLayout.NORTH);
        add(this.textField, BorderLayout.CENTER);
        if (hasButton) {
            add(open, BorderLayout.WEST);
            add(remove, BorderLayout.EAST);
        }
    }

    public JTextField getTextField() {
        return textField;
    }

    public void isShowSaveDialog(boolean b) {
        isShowSaveDialog = b;
    }

    public JLabel getDescribe() {
        return describe;
    }

    public String getText() {
        return text;
    }

    public void setSelectFileListener(SelectFileListener listener) {
        this.listener = listener;
    }

    public void setChooserFilter(FileFilter filter) {
        chooser.setFileFilter(filter);
    }

    public void setRemoveFileListener(RemoveFileListener listener) {
        this.removeFileListener = listener;
    }

    public void doRemove() {
        textField.setText("");
        remove.setVisible(false);
        if (removeFileListener != null) {
            removeFileListener.onRemoveFile();
        }
        System.out.println(this + "\nremove file.");
    }

    @Override
    public String toString() {
        return "TextFiledWithDescribe{" +
                "describe=" + describe.getText() +
                '}';
    }
}
