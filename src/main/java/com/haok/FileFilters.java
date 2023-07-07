package com.haok;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @author Haok224
 *
 */
public interface FileFilters {

    FileFilter PNG_FILE_FILTER = new FileFilter() {
        @Override
        public boolean accept(File f) {
            return f.isDirectory() || f.getAbsolutePath().endsWith(".png");
        }

        @Override
        public String getDescription() {
            return "图片文件(*.png)";
        }
    };

    FileFilter TTF_FILE_FILTER = new FileFilter() {
        @Override
        public boolean accept(File f) {
            return f.isDirectory() || f.getAbsolutePath().endsWith(".ttf");
        }

        @Override
        public String getDescription() {
            return "TrueType字体文件(*.ttf)";
        }
    };
}
