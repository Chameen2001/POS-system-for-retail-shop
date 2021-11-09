import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class STARTER extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("view/MainForm.fxml")));
        scene.getStylesheets().add("view/assests/style/addItemFormStyle.css");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
