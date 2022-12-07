module org.haok.resourcespackmaker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.alibaba.fastjson2;
    opens org.haok.resourcespackmaker to javafx.fxml;
    exports org.haok.resourcespackmaker;
    exports org.haok.resourcespackmaker.util;
    exports org.haok.resourcespackmaker.log;
    exports org.haok.resourcespackmaker.pack;
    opens org.haok.resourcespackmaker.pack to javafx.fxml;
}