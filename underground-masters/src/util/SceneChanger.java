package util;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneChanger {

	public static void change(ActionEvent event, String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(SceneChanger.class.getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource())
                                  .getScene()
                                  .getWindow();
            
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
