package controller.talent;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.talent.TalentDTO;

public class TalentDetailPopupController {

    @FXML
    private TextField talentNameField;

    private TalentDTO talent;

    public void setTalent(TalentDTO talent) {
        this.talent = talent;
        loadTalentDetails();
    }

    private void loadTalentDetails() {
        if (talent != null) {
            talentNameField.setText(talent.getName());
        }
    }

    // 수정 및 삭제 버튼 핸들러는 나중에 구현 예정
}