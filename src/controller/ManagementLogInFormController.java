package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagementLogInFormController {
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    private Pane pane;

    public void logInOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManagementForm.fxml"))));
        stage.show();
        Stage stage1 = (Stage) txtUserName.getScene().getWindow();
        stage1.close();
    }

    public void setPane(Pane pane){
        this.pane=pane;
    }
}
