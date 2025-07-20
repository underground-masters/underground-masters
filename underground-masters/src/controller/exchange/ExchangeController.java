package controller.exchange;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExchangeController implements Initializable {
	// ExchangeListView.fxml에서 찾아서 등록
//	@FXML private Button registerExchangeBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		registerExchangeBtn.setOnAction(event -> onShowRegisterExchangeBtnPopup(event));	
	}
	
	/*
	 * 교환글 등록 팝업창 
	 */
	@FXML
	private void onShowRegisterExchangePopupBtn(ActionEvent event) {
		try {
			// 1. FXML 로드
			Parent popupRoot = FXMLLoader.load(getClass().getResource("/view/exchange/CreateExchangePopup.fxml"));
			
			// 2. 새로운 Stage 생성
			Stage popupStage = new Stage();
			
			// 3. 모달창으로 만들기(부모창 클릭 차단)
			popupStage.initModality(Modality.WINDOW_MODAL);
			
			// 4. 팝업창 옵션
			popupStage.setTitle("교환글 등록");
			
			// 5. Scene 세팅 및 show
			popupStage.setScene(new Scene(popupRoot));
			popupStage.setResizable(false);
			popupStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
