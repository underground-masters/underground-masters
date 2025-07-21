package controller.exchange;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.exchange.ExchangeDAO;
import model.exchange.ExchangeDTO;

public class DeleteExchangePopupController implements Initializable {
	
	private final ExchangeDAO exchangeDAO = new ExchangeDAO(); // 공유

	// DeleteExchangePopup.fxml에서 찾아서 등록
	@FXML private Button deleteBtn;
	
	// 테이블 뷰(ExchangeController -> MyExchangeDetailPopupController)에게 전달 받은 DTO 저장
	private ExchangeDTO exchangeDTO; 
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	/*
	 * MyExchangeDetailPopupController에서 받은 DTO 저장
	 */
	public void setExchangeDTO(ExchangeDTO exchangeDTO) {
		this.exchangeDTO = exchangeDTO;
	}
	
	/*
	 * 교환글 삭제 메서드
	 * (UPDATE : IS_DELETE: 'N' -> 'Y')
	 */
	@FXML
	public void deleteExchange(ActionEvent event) throws ClassNotFoundException, SQLException {
		
		System.out.println("exchangeDTO: " + exchangeDTO);
		System.out.println("exchangeDTO.getExchangeId(): " + exchangeDTO.getExchangeId());
		// 1. 삭제 로직
		exchangeDAO.deleteExchange(exchangeDTO.getExchangeId().get());
		
		// 2. 팝업 창 닫기
	    ((Stage) deleteBtn.getScene().getWindow()).close();
	}

}
