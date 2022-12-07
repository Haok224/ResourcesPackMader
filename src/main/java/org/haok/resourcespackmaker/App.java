package org.haok.resourcespackmaker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.haok.resourcespackmaker.log.Logger;

public class App extends Application {
    static Stage primaryStage;
    static final String SEPARATOR = System.getProperty("file.separator");
    public static final Logger logger = new Logger();
    @Override
    public void start(Stage stage) throws Exception {
        logger.info("--App start now--");
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("资源包制作器");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            logger.info("--App end now--");
            stage.close();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}