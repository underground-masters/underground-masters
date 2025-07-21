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
import util.DBUtil;
import util.SceneChanger;

public class MemberModifyController implements Initializable {

	private Member member;
	private MemberDAO memberDao;
	
	@FXML private TextField nameField;
	@FXML private Label emailLabel;
	@FXML private PasswordField passwordField;
	@FXML private PasswordField checkPasswordField;
	@FXML private TextField phoneNumberField;
	
	@FXML private Label lblError;          // 오류 메시지 라벨
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		member = AuthenticationSession.getInstance().getMember();
		memberDao = MemberDAO.getInstance();
		
		nameField.setText(member.getName());
		emailLabel.setText(member.getEmail());
		phoneNumberField.setText(member.getPhoneNumber());
	}

	@FXML
	public void saveAction(ActionEvent event) {
		
		// 오류 초기화
        lblError.setText("");
                
        String name = nameField.getText();
		String password = passwordField.getText();
		String checkPassword = checkPasswordField.getText();
		String phoneNumber = phoneNumberField.getText();
		
		try {
			validateCheckPassword(password, checkPassword);
			validatePhoneNumber(phoneNumber);
			
			String query = "{ call SP_UPDATE_MEMBER(?, ?, ?, ?) }";
			
			DBUtil.dbExecuteUpdate(query, member.getMemberId(), name, password, phoneNumber);
			
			// 회원 정보 리로드
			AuthenticationSession.getInstance().reload(memberDao);
			
			SceneChanger.change(event, "/view/member/MemberDetailView.fxml", "회원 정보");
			
		} catch (ValidationException ex) {
            // 검증 예외: 메시지만 사용자에게 보여줌
            lblError.setText(ex.getMessage());
        } catch (ClassNotFoundException | SQLException ex) {
			// db 오류
			ex.printStackTrace();
			lblError.setText("회원 정보 수정 중 오류가 발생했습니다.");
		}
	}
		
	@FXML
	public void cancelAction(ActionEvent event) {
		SceneChanger.change(event, "/view/member/MemberDetailView.fxml", "회원 정보");
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
