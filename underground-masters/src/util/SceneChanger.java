package util;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * 작성자: 정의탁
 */
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
	
	public static void openModal(ActionEvent event, String fxmlPath, String title) {
		try {
			FXMLLoader loader = new FXMLLoader(SceneChanger.class.getResource(fxmlPath));
			Parent root = loader.load();
			Stage owner = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();
			
			Stage modal = new Stage();
			modal.initOwner(owner);
			
			modal.setTitle(title);
			modal.initModality(Modality.APPLICATION_MODAL);
			modal.setResizable(false);
			modal.setScene(new Scene(root));
			modal.showAndWait();

		} catch (IOException e) {
			throw new RuntimeException("모달 로드 실패: " + fxmlPath, e);
		}
	}
}
