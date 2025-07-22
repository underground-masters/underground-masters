package controller.matching;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.matching.MatchingDAO;
import model.matching.MatchingDTO;

public class MatchingDetailPopupController implements Initializable {

    @FXML private Label matchingDetailLabel;
    @FXML private Button acceptBtn;
    @FXML private Button rejectBtn;

    private MatchingDTO matchingDTO;
    private final MatchingDAO dao = new MatchingDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        acceptBtn.setOnAction(event -> onAccept());
        rejectBtn.setOnAction(event -> onReject());
    }

    public void setMatchingData(MatchingDTO dto) {
        this.matchingDTO = dto;
        matchingDetailLabel.setText(
            "상대 고수 : " + dto.getRequesterName() + '\n' +
            "상대 재능 : " + dto.getRequestedTalent() + '\n' +
            "상세 설명 : " + dto.getExchangeContent() + '\n' +
            "등록일 : " + dto.getRequestDate() + '\n' +
            "상태 : " + dto.getStatus()
        );
    }

    @FXML
    private void onAccept() {
        updateStatusAndClose("수락");
    }

    @FXML
    private void onReject() {
        updateStatusAndClose("거절");
    }

    private void updateStatusAndClose(String newStatus) {
        try {
            dao.updateMatchingStatus(matchingDTO.getMatchingId(), newStatus);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closePopup();
        }
    }

    private void closePopup() {
        Stage stage = (Stage) acceptBtn.getScene().getWindow();
        stage.close();
    }
}