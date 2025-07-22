package controller.matching;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.matching.MatchingDTO;

public class MatchingDetailPopupController implements Initializable {
	
	// MatchingDetailPopup.fxml에서 찾아서 등록
    @FXML private Label matchingDetailLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
	}
	
    public void setMatchingData(MatchingDTO matchingDTO) {
    	System.out.println(matchingDTO.getExchangeContent());
    	matchingDetailLabel.setText(
                "상대 고수 : " + matchingDTO.getRequesterName() + '\n' +
                "상대 재능 : " + matchingDTO.getRequestedTalent() + '\n' +
                "상세 설명 : " + matchingDTO.getExchangeContent() + '\n' +
                "등록일 : " + matchingDTO.getRequestDate() + '\n'
            );
    }
}
