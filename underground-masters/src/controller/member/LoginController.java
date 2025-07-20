package controller.member;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.member.Member;
import util.AuthenticationSession;
import util.DBUtil;
import util.SceneChanger;

public class LoginController implements Initializable {

	@FXML private TextField emailField;
	@FXML private PasswordField passwordField;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@FXML
	public void loginAction(ActionEvent event) {
		String email = emailField.getText();
		String password = passwordField.getText();
		
		try {
			loginService(email, password);
			
			// 로그인에 성공하면, 교환의 장 목록 페이지로 이동.
			Member member = AuthenticationSession.getInstance().getMember();
			
			System.out.println(AuthenticationSession.getInstance().isAuthenticated());
			System.out.println(member.getClass());
			
			
		} catch (ClassNotFoundException | SQLException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
	
	@FXML
	public void signUpAction(ActionEvent event) {
		SceneChanger.change(event, "/view/member/SignUpView.fxml", "sign up");
	}
	
	public void loginService(String email, String password) 
			throws ClassNotFoundException, SQLException {
		
		Member member;
		String query = "SELECT * FROM member m WHERE m.email = ? AND m.password = ?";

		ResultSet resultSet = DBUtil.dbExecuteQuery(query, email, password);
		if(resultSet.next()) {
			member = Member.builder()
					.memberId(resultSet.getInt("member_id"))
					.name(resultSet.getString("name"))
					.email(resultSet.getString("email"))
					.phoneNumber(resultSet.getString("phone_number"))
					.build();		
		} else {
			throw new NoSuchElementException("member not found. (email = " + email + ")");
		}
		
		AuthenticationSession.getInstance().setMember(member);
	}
}
