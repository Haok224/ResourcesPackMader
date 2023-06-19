package com.haok.components;

import java.io.File;
import java.util.EventListener;

public interface SelectFileListener extends EventListener {
    void onFileSelect(File file) throws Exception;
}
