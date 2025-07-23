package controller.member;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.AlertUtil;
import util.DBUtil;
import util.SceneChanger;

public class SignUpController implements Initializable{

	@FXML private TextField nameField;
	@FXML private TextField emailField;
	@FXML private PasswordField passwordField;
	@FXML private PasswordField checkPasswordField;
	@FXML private TextField phoneNumberField;
	
	@FXML private Button signUpButton;
	
	@FXML private Label lblError;          // 오류 메시지 라벨
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	public void signUpAction(ActionEvent event) {
		
		// 오류 초기화
        lblError.setText("");
		
		String name = nameField.getText();
		String email = emailField.getText();
		String password = passwordField.getText();
		String checkPassword = checkPasswordField.getText();
		String phoneNumber = phoneNumberField.getText();
		
		try {
			validateEmailFormat(email);
			validateCheckPassword(password, checkPassword);
			validatePhoneNumber(phoneNumber);
			validateEmailDuplicate(email);
						
			String query = "{ call SP_CREATE_MEMBER(?, ?, ?, ?) }";
			
			DBUtil.dbExecuteUpdate(query, name, email, password, phoneNumber);			
			
			AlertUtil.showSuccess(
				signUpButton.getScene().getWindow(),
			    "회원이 성공적으로 등록되었습니다."
			);
			
			// 회원이 정상 등록된 경우, 로그인 페이지로 리다이렉트.
			SceneChanger.change(event, "/view/member/LoginView.fxml", "login");
			
		} catch (ValidationException ex) {
            // 검증 예외: 메시지만 사용자에게 보여줌
            lblError.setText(ex.getMessage());
        } catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
			lblError.setText("회원가입 처리 중 오류가 발생했습니다.");
		}
	}
	
	@FXML
	public void cancelAction(ActionEvent event) {
		SceneChanger.change(event, "/view/member/LoginView.fxml", "로그인");
	}
	
	// 이메일 중복 확인.( 유효성 검사 통과 시, true )
	private boolean validateEmailDuplicate(String email) 
			throws ClassNotFoundException, SQLException {
		
		String query = "SELECT COUNT(*) as count FROM member m WHERE m.email = ?";
		
		ResultSet resultSet = DBUtil.dbExecuteQuery(query, email);
		if(resultSet.next()) {
			if (resultSet.getInt("count") > 0) {
				throw new ValidationException("이미 등록되어 있는 이메일 입니다. (email = " + email + ")");
			}
		}		
		return true;
	}
	
	// 이메일 형식 확인.
	private void validateEmailFormat(String email) {
        if (email.isEmpty() || !email.matches(".+@.+\\..+")) {
            throw new ValidationException("유효한 이메일 주소를 입력하세요.");
        }
    }
	
	// 핸드폰 번호 형식 확인.
	private void validatePhoneNumber(String phoneNumber) {
        String regex = "^01[016789]-\\d{3,4}-\\d{4}$";
        if (!phoneNumber.matches(regex)) {
            throw new ValidationException("유효한 핸드폰 번호를 입력하세요. (예: 010-1234-5678)");
        }
    }
	
	// 비밀번호/확인 일치 여부 검증
    private void validateCheckPassword(String password, String checkPassword) {
        if (password.isEmpty() || checkPassword.isEmpty()) {
            throw new ValidationException("비밀번호를 모두 입력하세요.");
        }
        if (!password.equals(checkPassword)) {
            throw new ValidationException("비밀번호와 확인이 일치하지 않습니다.");
        }
    }
	
	// 검증 전용 예외
    private static class ValidationException extends RuntimeException {
        ValidationException(String message) {
            super(message);
        }
    }
}
