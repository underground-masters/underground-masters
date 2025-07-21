package controller.exchange;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.Getter;
import model.exchange.ExchangeDTO;

@Getter
public abstract class ExchangeDetailPopupController implements Initializable {
	
	// 교환글 목록에서 전달 받을 데이터
	private ExchangeDTO exchangeDTO;
	
	/*
	 * DTO 저장하는 메서드
	 */
	public void setExchangeDTO(ExchangeDTO exchangeDTO) {
	    this.exchangeDTO = exchangeDTO;
	}
	
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
