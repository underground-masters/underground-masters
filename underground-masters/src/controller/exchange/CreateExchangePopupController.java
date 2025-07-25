package controller.exchange;

/*
 * 작성자: 김경아
 */
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.exchange.ExchangeDAO;
import util.AlertUtil;

public class CreateExchangePopupController implements Initializable {
	
	private final ExchangeDAO exchangeDAO = new ExchangeDAO(); // 공유
	
	// CreateExchangePopup.fxml에서 찾아서 등록
	@FXML private ComboBox<String> talentComboBox;
	@FXML private TextArea descTextField;
	@FXML private Button registerBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 콤보박스에 재능 리스트 조회
		ObservableList<String> talentList;
		try {
			talentList = exchangeDAO.findTalentList();
			talentComboBox.setItems(talentList);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 교환글 등록 메서드
	 */
	@FXML
	public void onClickRegisterBtn(ActionEvent event) {
		try {
			String selectedTalent = talentComboBox.getValue(); // 선택한 재능
			String description = descTextField.getText(); // 상세설명
			
			exchangeDAO.createExchange(selectedTalent, description); // DB에 저장
			
			AlertUtil.showSuccess(
			    registerBtn.getScene().getWindow(),
			    "교환글이 성공적으로 등록되었습니다."
			);
			
			((Stage) registerBtn.getScene().getWindow()).close(); // 닫기 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
