package controller.member;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.member.Member;
import util.AuthenticationSession;
import util.SceneChanger;

public class MemberDetailController implements Initializable {

	private Member member;
	
	@FXML private Label nameLabel;
	@FXML private Label emailLabel;
	@FXML private Label phoneNumberLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
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
