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

/**
 * 작성자: 정의탁
 */
public class MyPageController extends NavbarController implements Initializable {

	private Member member;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
	
	@FXML
	//로그아웃 기능 추가_20250723_ycson
	public void logoutAction(javafx.event.ActionEvent event) {
	    // 1. 인증 세션 초기화
	    AuthenticationSession.getInstance().setMember(null);
	    // 2. 로그인 화면으로 전환
	    SceneChanger.change(event, "/view/member/LoginView.fxml", "로그인");
	}
	
}
