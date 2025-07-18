package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AppMain extends Application {
	
	// JavaFX 애플리케이션의 시작 지점
	@Override
	public void start(Stage primaryStage) throws Exception {
		// FXML 파일을 로드하여 UI의 루트 노드 생성
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/member/LoginView.fxml")); 	// Load RootLayout.fxml
		AnchorPane root = (AnchorPane) loader.load();
		
		// 루트 노드로 Scene 객체 생성
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("재야의 고수들");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	// 프로그램의 진입점
	public static void main(String[] args) {
		launch(args); // AppMain 객체 생성 및 메인 윈도우 생성
	}
}