package controller.talent;

import model.talent.TalentDTO;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.talent.TalentDAO;
import util.AlertUtil;
import util.AuthenticationSession;

/*
 * 작성자: 손윤찬
 */
public class CreateTalentPopupController {

    @FXML
    private TextField talentNameField;
    
    @FXML
    private Button saveBtn;

    private TalentListController talentListController; // 목록 갱신용

    public void setTalentListController(TalentListController controller) {
        this.talentListController = controller;
    }

    @FXML
    private void onRegisterTalentClicked() {
        String name = talentNameField.getText().trim();

        if (name.isEmpty()) {
            // 추후 경고창을 띄우는 코드 추가 가능
            System.out.println("재능 이름을 입력해주세요.");
            return;
        }

        int memberId = AuthenticationSession.getInstance().getMember().getMemberId();

        TalentDTO dto = new TalentDTO();
        dto.setName(name);
        dto.setMemberId(memberId);

        TalentDAO dao = new TalentDAO();
        dao.createTalent(dto); // DB에 등록

        if (talentListController != null) {
            talentListController.loadTalentList(); // 목록 갱신
        }

        AlertUtil.showSuccess(
    		saveBtn.getScene().getWindow(),
		    "재능이 성공적으로 등록 되었습니다."
		);
        
        // 창 닫기
        Stage stage = (Stage) talentNameField.getScene().getWindow();
        stage.close();
    }
}