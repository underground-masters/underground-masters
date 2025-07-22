package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Window;

public class AlertUtil {

    private AlertUtil() {
        // 인스턴스 생성 금지
    }

    public static void showInfo(Window owner, String title, String message) {
        createAlert(owner, AlertType.INFORMATION, title, message).showAndWait();
    }

    public static void showSuccess(Window owner, String message) {
    	// INFORMATION 타입으로 Alert 생성
        Alert alert = createAlert(owner, AlertType.INFORMATION, "성공", message);
        
        // OK 버튼만 남기고 설정
        alert.getButtonTypes().setAll(ButtonType.OK);
        
        // 다이얼로그 표시 및 대기
        alert.showAndWait();
    }

    public static void showWarning(Window owner, String title, String message) {
        createAlert(owner, AlertType.WARNING, title, message).showAndWait();
    }

    public static void showError(Window owner, String title, String message) {
        createAlert(owner, AlertType.ERROR, title, message).showAndWait();
    }

    private static Alert createAlert(Window owner, AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.initOwner(owner);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert;
    }
}