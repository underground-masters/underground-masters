package controller.common;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import util.SceneChanger;

public abstract class NavbarController {

	@FXML
	public void exchangeListBtn(ActionEvent event) {
		SceneChanger.change(event, "/view/exchange/ExchangeListView.fxml", "교환의 장");
	}
	
	@FXML
	public void receiveListBtn(ActionEvent event) {
		SceneChanger.change(event, "/view/matching/MatchingReceiveListView.fxml", "받은 목록");
	}
	
	@FXML
	public void sendListBtn(ActionEvent event) {
		SceneChanger.change(event, "/view/matching/MatchingSendListView.fxml", "보낸 목록");
	}
	
	@FXML
	public void myPageBtn(ActionEvent event) {
		SceneChanger.change(event, "/view/member/MyPageView.fxml", "마이 페이지");
	}
}
