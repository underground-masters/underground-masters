package controller.matching;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import model.matching.MatchingDAO;
import model.matching.MatchingDTO;

/*
 * 작성자: 김경아
 */
public class MatchingDetailPopupController implements Initializable {

    @FXML private TextArea matchingDetailLabel;
    @FXML private Button acceptBtn;
    @FXML private Button rejectBtn;

    private MatchingDTO matchingDTO;
    private final MatchingDAO dao = new MatchingDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 수락 버튼에 클릭 이벤트 핸들러 연결
        acceptBtn.setOnAction(event -> onAccept());

        // 거절 버튼에 클릭 이벤트 핸들러 연결
        rejectBtn.setOnAction(event -> onReject());
    }

    /**
     * 팝업창에 매칭 정보 표시
     */
    public void setMatchingData(MatchingDTO dto) {
        this.matchingDTO = dto;
        matchingDetailLabel.setText(
            "상대 고수 : " + dto.getRequesterName() + '\n' +
            "상대 재능 : " + dto.getRequestedTalent() + '\n' +
            "등록일 : " + dto.getRequestDate() + '\n' +
            "상태 : " + dto.getStatus()
        );
        
        if ("수락".equals(dto.getStatus()) || "거절".equals(dto.getStatus())) {
            acceptBtn.setVisible(false);
            rejectBtn.setVisible(false);
        }
    }

    /**
     * 수락 버튼 클릭 시 실행
     */
    @FXML
    private void onAccept() {
        updateStatusAndClose("수락");
    }

    /**
     * 거절 버튼 클릭 시 실행
     */
    @FXML
    private void onReject() {
        updateStatusAndClose("거절");
    }

    /**
     * 상태 변경 후 팝업창 닫기
     */
    private void updateStatusAndClose(String newStatus) {
        try {
            dao.updateMatchingStatus(matchingDTO.getMatchingId(), newStatus);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closePopup();
        }
    }

    /**
     * 팝업창 닫기
     */
    private void closePopup() {
        Stage stage = (Stage) acceptBtn.getScene().getWindow();
        stage.close();
    }
}