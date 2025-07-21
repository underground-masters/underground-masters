package controller.talent;

import model.talent.TalentDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.talent.TalentDAO;
import util.AuthenticationSession;

import java.io.IOException;
import java.util.List;

public class TalentListController {

    @FXML
    private VBox talentNameBox;

    @FXML
    private VBox talentDateBox;

    private final TalentDAO talentDAO = new TalentDAO();

    @FXML
    public void initialize() {
        loadTalentList();
    }

    public void loadTalentList() {
        talentNameBox.getChildren().remove(1, talentNameBox.getChildren().size());
        talentDateBox.getChildren().remove(1, talentDateBox.getChildren().size());

        int memberId = AuthenticationSession.getInstance().getMember().getMemberId();
        List<TalentDTO> talents = talentDAO.findTalentListByMemberId(memberId);

        for (TalentDTO talent : talents) {
            Label nameLabel = new Label(talent.getName());
            nameLabel.setPrefWidth(180.0);
            nameLabel.setStyle("-fx-padding: 10 0 10 16; -fx-font-size:14px; -fx-text-fill:#525252;");

            Label dateLabel = new Label(talent.getCreatedAt());
            dateLabel.setPrefWidth(180.0);
            dateLabel.setStyle("-fx-padding: 10 0 10 16; -fx-font-size:14px; -fx-text-fill:#525252; -fx-font-weight:bold;");

            talentNameBox.getChildren().add(nameLabel);
            talentDateBox.getChildren().add(dateLabel);
        }
    }

    @FXML
    private void onAddTalentClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateTalentPopup.fxml"));
            Parent root = loader.load();

            // 팝업 컨트롤러에 현재 컨트롤러 넘겨줌
            CreateTalentPopupController popupController = loader.getController();
            popupController.setTalentListController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("재능 등록");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // 팝업 닫힌 뒤 재능 목록 다시 로딩
            loadTalentList();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}