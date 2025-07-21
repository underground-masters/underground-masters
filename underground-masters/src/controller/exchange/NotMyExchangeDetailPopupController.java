package controller.exchange;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.exchange.ExchangeDAO;
import model.exchange.ExchangeDTO;

public class NotMyExchangeDetailPopupController extends ExchangeDetailPopupController {
	@FXML private Label exchangeDetailLabel;
	@FXML private ComboBox<String> talentComboBox;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 콤보박스에 재능 리스트 조회
		ObservableList<String> talentList;
		try {
			talentList = ExchangeDAO.findTalentList();
			talentComboBox.setItems(talentList);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	@Override
    public void setExchangeData(ExchangeDTO exchangeDTO) {
        // 부모 메서드를 이용해서 라벨 세팅
        doLabelSetting(exchangeDetailLabel, exchangeDTO);
    }
}
