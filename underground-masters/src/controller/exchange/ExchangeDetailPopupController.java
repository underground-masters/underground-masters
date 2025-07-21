package controller.exchange;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.exchange.ExchangeDTO;

public abstract class ExchangeDetailPopupController implements Initializable {
	
	/*
	 * 라벨에 data 넣기
	 */
    public void doLabelSetting(Label label, ExchangeDTO exchangeDTO) {
        label.setText(
            "고수 : " + exchangeDTO.getMemberId().get() + '\n' +
            "재능 : " + exchangeDTO.getTitle().get() + '\n' +
            "상세설명 : " + exchangeDTO.getContent().get() + '\n' +
            "등록일 : " + exchangeDTO.getCreateAt().get() + '\n'
        );
    }
    
    public abstract void setExchangeData(ExchangeDTO exchangeDTO);
}
