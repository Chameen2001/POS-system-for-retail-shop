package util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static void validate(LinkedHashMap<TextField, Pattern> regExMap, Button button) {

        for (TextField textField : regExMap.keySet()) {
            Pattern pattern = regExMap.get(textField);
            if (!textField.getText().isEmpty()) {
                if (!pattern.matcher(textField.getText()).matches()) {
                    textField.getParent().setStyle("-fx-border-color: rgba(255,0,0,0.5)");
                    button.setDisable(true);
                } else {
                    textField.getParent().setStyle("-fx-border-color: rgba(0,225,0,0.5)");
                }
            } else {
                textField.getParent().setStyle(null);
                button.setDisable(true);
            }
        }
    }

    public static void requestFocus(LinkedHashMap<TextField, Pattern> regExMap, Button btnSave) {
        TextField[] textFields = new TextField[regExMap.size()];
        int i = 0;
        for (TextField textField : regExMap.keySet()) {
            textFields[i++] = textField;
        }
        for (int j = 0; j < textFields.length; j++) {
            if (textFields[j].getParent().getStyle().equals("-fx-border-color: rgba(0,225,0,0.5)")) {
                if (textFields.length == j + 1) {
                    for (int k = 0; k < textFields.length; k++) {
                        if (!textFields[k].getParent().getStyle().equals("-fx-border-color: rgba(0,225,0,0.5)")) {
                            textFields[k].requestFocus();
                            return;
                        }
                    }
                    btnSave.setDisable(false);
                    return;
                }
                if (!textFields[j + 1].getParent().getStyle().equals("-fx-border-color: rgba(0,225,0,0.5)")) {
                    textFields[j + 1].requestFocus();
                    return;
                }
            } else {
                if (textFields[j].isFocused()){
                    textFields[j].requestFocus();
                    return;
                }
            }
        }
    }
}
