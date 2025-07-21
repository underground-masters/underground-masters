package controller.talent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.talent.*;
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
            nameLabel.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {
                    openTalentDetailPopup(talent);
                }
            });

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/talent/CreateTalentPopup.fxml"));
            Parent root = loader.load();

            CreateTalentPopupController popupController = loader.getController();
            popupController.setTalentListController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("재능 등록");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadTalentList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openTalentDetailPopup(TalentDTO talent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/talent/TalentDetailPopup.fxml"));
            Parent root = loader.load();

            TalentDetailPopupController controller = loader.getController();
            controller.setTalent(talent);
            controller.setTalentListController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("재능 상세");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}