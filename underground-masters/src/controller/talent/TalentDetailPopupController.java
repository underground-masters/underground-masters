package controller.talent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.talent.*;
import util.AlertUtil;

/*
 * 작성자: 손윤찬
 */
public class TalentDetailPopupController {

    @FXML
    private TextField talentNameField;

    @FXML private Button updateBtn;
    @FXML private Button deleteBtn;
    
    private TalentDTO talentDTO; // 선택된 재능 정보
    private TalentListController talentListController; // 목록 갱신용

    public void setTalent(TalentDTO talentDTO) {
        this.talentDTO = talentDTO;
        talentNameField.setText(talentDTO.getName());
    }

    public void setTalentListController(TalentListController controller) {
        this.talentListController = controller;
    }

    @FXML
    private void onUpdateTalentClicked() {
        String newName = talentNameField.getText().trim();
        if (newName.isEmpty()) {
            System.out.println("재능 이름을 입력해주세요.");
            return;
        }

        talentDTO.setName(newName);
        new TalentDAO().updateTalent(talentDTO); // DB 업데이트

        if (talentListController != null) {
            talentListController.loadTalentList(); // 목록 갱신
        }

        AlertUtil.showSuccess(
    		updateBtn.getScene().getWindow(),
		    "재능이 성공적으로 수정 되었습니다."
		);
        
        closeWindow();
    }

    @FXML
    private void onDeleteTalentClicked() {
        new TalentDAO().deleteTalent(talentDTO.getTalentId()); // DB에서 삭제 처리

        if (talentListController != null) {
            talentListController.loadTalentList(); // 목록 갱신
        }

        AlertUtil.showSuccess(
    		deleteBtn.getScene().getWindow(),
		    "재능이 성공적으로 삭제 되었습니다."
		);
        
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) talentNameField.getScene().getWindow();
        stage.close();
    }
}
