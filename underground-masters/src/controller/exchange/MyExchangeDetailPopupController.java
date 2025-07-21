package controller.exchange;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.exchange.ExchangeDTO;

public class MyExchangeDetailPopupController extends ExchangeDetailPopupController {
	
	@FXML private Label exchangeDetailLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@Override
    public void setExchangeData(ExchangeDTO exchangeDTO) {
        // 부모 메서드를 이용해서 라벨 세팅
        doLabelSetting(exchangeDetailLabel, exchangeDTO);
    }

}
