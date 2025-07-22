package controller.member;

import java.net.URL;
import java.util.ResourceBundle;

import controller.common.NavbarController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.member.Member;
import util.AuthenticationSession;
import util.SceneChanger;

public class MyPageController extends NavbarController implements Initializable {

	private Member member;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		member = AuthenticationSession.getInstance().getMember();
	}

	@FXML
	public void memberDetailAction(ActionEvent event) {
		SceneChanger.change(event, "/view/member/MemberDetailView.fxml", "회원 정보");
	}
	
	@FXML
	public void talentListAction(ActionEvent event) {
		SceneChanger.change(event, "/view/talent/TalentListView.fxml", "재능 관리");
	}
	
	@FXML
	public void matchingHistoryAction(ActionEvent event) {
		SceneChanger.change(event, "/view/member/MatchingHistory.fxml", "매칭 이력");
	}
	
}
