package controller.exchange;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.exchange.ExchangeDTO;

public class MyExchangeDetailPopupController extends ExchangeDetailPopupController {
	
	// MyExchangeDetailPopup.fxml에서 찾아서 등록
	@FXML private Label exchangeDetailLabel;
	@FXML private Button deleteBtn;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	/**
	 * 라벨에 데이터 매핑하는 메서드
	 */
	@Override
    public void setExchangeData(ExchangeDTO exchangeDTO) {
		
		// 부모(ExchangeDetailPopupController)의 필드에 저장
		setExchangeDTO(exchangeDTO); 
		
        // 부모 메서드를 이용해서 라벨 세팅
        doLabelSetting(exchangeDetailLabel, exchangeDTO);
    }
	
	/*
	 * 삭제 버튼 클릭 메서드
	 */
	@FXML
	public void onClickDeleteBtn(ActionEvent event) throws IOException {
		
		// 1. FXML 파일을 로딩할 FXMLLoader 객체를 생성(fxml 등록)
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/exchange/DeleteExchangePopup.fxml"));
		Parent popupRoot = loader.load();
		DeleteExchangePopupController deleteExchangePopupController = loader.getController();
		deleteExchangePopupController.setExchangeDTO(getExchangeDTO()); // 부모(ExchangeDetailPopupController)의 DTO 전달
		
		// 2.  새로운 Stage 생성
		Stage deletePopupStage = new Stage(StageStyle.UTILITY);
		
		// 3. 모달창으로 만들기(부모창 클릭 차단)
		deletePopupStage.initModality(Modality.WINDOW_MODAL);
		
		// 4. 팝업창 옵션
		deletePopupStage.setTitle("삭제");
		
		// 5. Scene 세팅 및 show
		Scene scene = new Scene(popupRoot);
		deletePopupStage.setScene(scene);
		deletePopupStage.setResizable(false);
		
		// 6. 팝업창이 닫힐때 까지 대기
		deletePopupStage.showAndWait();
		((Stage) deleteBtn.getScene().getWindow()).close(); // 자기 자신도 닫기

	}

}
