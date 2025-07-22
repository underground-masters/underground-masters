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
    private final MatchingDAO matchingDAO = new MatchingDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        acceptBtn.setOnAction(event -> {
            updateStatus("수락");
            closeWindow();
        });

        rejectBtn.setOnAction(event -> {
            updateStatus("거절");
            closeWindow();
        });
    }

    public void setMatchingData(MatchingDTO matchingDTO) {
        this.matchingDTO = matchingDTO;

        matchingDetailLabel.setText(
                "상대 고수 : " + matchingDTO.getRequesterName() + '\n' +
                "상대 재능 : " + matchingDTO.getRequestedTalent() + '\n' +
                "상세 설명 : " + matchingDTO.getExchangeContent() + '\n' +
                "등록일 : " + matchingDTO.getRequestDate() + '\n' +
                "상태 : " + matchingDTO.getStatus()
        );
    }

    private void updateStatus(String newStatus) {
        try {
            matchingDAO.updateMatchingStatus(matchingDTO.getMatchingId(), newStatus);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) acceptBtn.getScene().getWindow();
        stage.close();
    }
}
