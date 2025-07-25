package controller.member;

import java.net.URL;
import java.util.ResourceBundle;

import controller.common.NavbarController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.member.Member;
import util.AuthenticationSession;
import util.SceneChanger;

/**
 * 작성자: 정의탁
 */
public class MemberDetailController extends NavbarController implements Initializable {

	private Member member;
	
	@FXML private Label nameLabel;
	@FXML private Label emailLabel;
	@FXML private Label phoneNumberLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		member = AuthenticationSession.getInstance().getMember();
		
		nameLabel.setText(member.getName());
		emailLabel.setText(member.getEmail());
		phoneNumberLabel.setText(member.getPhoneNumber());
	}

	@FXML
	public void memberModifyAction(ActionEvent event) {
		// 회원 정보 수정 modal
		SceneChanger.change(event, "/view/member/MemberModifyView.fxml", "회원 정보 수정");
	}
}
