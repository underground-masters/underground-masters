package controller.member;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.member.Member;
import model.member.MemberDAO;
import util.AuthenticationSession;
import util.SceneChanger;

/**
 * 작성자: 정의탁
 */
public class LoginController implements Initializable {

	private MemberDAO memberDao;
	
	@FXML private TextField emailField;
	@FXML private PasswordField passwordField;
	
	@FXML private Label lblError;          // 오류 메시지 라벨
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		memberDao = MemberDAO.getInstance();
		//Enter키 로그인 20250723_ycson
		passwordField.setOnAction(e -> loginAction(e));
	}

	@FXML
	public void loginAction(ActionEvent event) {
		
		// 오류 초기화
        lblError.setText("");
		
		String email = emailField.getText();
		String password = passwordField.getText();
		
		try {
			loginService(email, password);
			SceneChanger.change(event, "/view/exchange/ExchangeListView.fxml", "교환의 장");
			
		} catch (ValidationException ex) {
			// 검증 예외: 메시지만 사용자에게 보여줌
			lblError.setText(ex.getMessage());
		} catch (ClassNotFoundException | SQLException ex) {
			// db 오류
			ex.printStackTrace();
			lblError.setText("로그인 중 오류가 발생했습니다.");
		}
	}
	
	@FXML
	public void signUpAction(ActionEvent event) {
		SceneChanger.change(event, "/view/member/SignUpView.fxml", "sign up");
	}
	
	public void loginService(String email, String password) 
			throws ClassNotFoundException, SQLException {
		
		Member member = memberDao.findByEmail(email);
		if (member == null || !member.matchesPassword(password)) {
			throw new ValidationException("로그인 정보를 확인해주세요.");
		}			
		
		AuthenticationSession.getInstance().setMember(member);		
	}
	
	// 검증 전용 예외
    private static class ValidationException extends RuntimeException {
        ValidationException(String message) {
            super(message);
        }
    }
}