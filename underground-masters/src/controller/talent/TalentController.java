package controller.talent;

import model.talent.TalentDTO;
import model.talent.TalentDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class TalentController {

    @FXML private VBox talentNameBox;
    @FXML private VBox talentDateBox;

    private int memberId = 3; // 로그인 유저 ID, 임시로 3번 사용
    private TalentDAO talentDAO = new TalentDAO();

    @FXML
    public void initialize() {
        loadMyTalents();
    }

    public void onAddTalentClicked(MouseEvent e) {
        System.out.println("재능 등록 팝업 열기"); // TODO: 팝업 띄우기
    }

    private void loadMyTalents() {
        talentNameBox.getChildren().remove(1, talentNameBox.getChildren().size()); // 헤더 제외 삭제
        talentDateBox.getChildren().remove(1, talentDateBox.getChildren().size());

        List<TalentDTO> list = talentDAO.findTalentListByMemberId(memberId);

        for (TalentDTO dto : list) {
            Label nameLabel = new Label(dto.getName());
            nameLabel.setStyle("-fx-padding: 10 0 10 16; -fx-font-size:14px; -fx-text-fill:#525252;");
            nameLabel.setPrefWidth(180.0);

            Label dateLabel = new Label(dto.getCreatedAt());
            dateLabel.setStyle("-fx-padding: 10 0 10 16; -fx-font-size:14px; -fx-text-fill:#525252; -fx-font-weight:bold;");
            dateLabel.setPrefWidth(180.0);

            HBox nameRow = new HBox(nameLabel);
            nameRow.setStyle("-fx-background-color: #FDFDFD; -fx-border-color: #DCDCDC; -fx-border-width:0 0 1 0;");
            HBox dateRow = new HBox(dateLabel);
            dateRow.setStyle("-fx-background-color: #FDFDFD; -fx-border-color: #DCDCDC; -fx-border-width:0 1 1 0;");

            talentNameBox.getChildren().add(nameRow);
            talentDateBox.getChildren().add(dateRow);
        }
    }
}