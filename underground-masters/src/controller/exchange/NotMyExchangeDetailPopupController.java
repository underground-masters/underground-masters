package controller.exchange;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.exchange.ExchangeDAO;
import model.exchange.ExchangeDTO;
import model.matching.MatchingDAO;

public class NotMyExchangeDetailPopupController extends DetailPopupController {
	
	private final ExchangeDAO exchangeDAO = new ExchangeDAO(); // 공유
	private final MatchingDAO matchingDAO = new MatchingDAO();
	
	// MyExchangeDetailPopup.fxml에서 찾아서 등록
	@FXML private TextArea exchangeDetailLabel;
	@FXML private ComboBox<String> talentComboBox;
	@FXML private Button requestBtn;
	
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
		exchangeDetailLabel.setFocusTraversable(false); // 포커스 안 받게 설정
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
	
	/**
	 * 매칭 요청 버튼 클릭 메서드
	 * @param event
	 * @throws IOException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@FXML
	public void onClickMatchingRequestBtn(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		ExchangeDTO exchangeDTO = getExchangeDTO(); // 부모(ExchangeDetailPopupController)의 DTO 전달
		int exchangeId = exchangeDTO.getExchangeId().get(); // 교환글 id
		String selectedTalent = talentComboBox.getValue();  // 선택한 재능
		
		matchingDAO.createMatching(exchangeId, selectedTalent); // DB에 저장
		
		((Stage) requestBtn.getScene().getWindow()).close(); // 화면 닫기
	}
}
