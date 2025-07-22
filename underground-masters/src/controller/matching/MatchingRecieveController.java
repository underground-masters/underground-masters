package controller.matching;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.matching.MatchingDAO;
import model.matching.MatchingDTO;
import model.member.Member;
import util.AuthenticationSession;

public class MatchingRecieveController implements Initializable {

    @FXML private TableView<MatchingDTO> MatchingTableView;
    @FXML private TableColumn<MatchingDTO, String> talentName;
    @FXML private TableColumn<MatchingDTO, String> memberName;
    @FXML private TableColumn<MatchingDTO, String> status;
    @FXML private TableColumn<MatchingDTO, String> requestDate;

    private Member member;
    
    private final MatchingDAO dao = new MatchingDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	member = AuthenticationSession.getInstance().getMember(); // 로그인한 사용자
    	
        talentName.setCellValueFactory(new PropertyValueFactory<>("requestedTalent"));
        memberName.setCellValueFactory(new PropertyValueFactory<>("requesterName"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        requestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));

        loadMatchingList();
    }

    public void loadMatchingList() {
//        int memberId = AuthenticationSession.getInstance().getMember().getMemberId();
    	int memberId = 2;

        try {
            List<MatchingDTO> result = dao.findReceivedMatchingList(memberId);
            ObservableList<MatchingDTO> list = FXCollections.observableArrayList(result);
            MatchingTableView.setItems(list);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
	 * 테이블뷰의 행을 더블클릭했을 때 팝업창을 띄워 매칭 요청 상세를 보여주는 메서드
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
    @FXML
    private void onShowDetailPopupBtn(MouseEvent event) throws ClassNotFoundException, SQLException, IOException {
    	// 0. 더블클릭(2번 클릭)이 아닐 때는 아무 것도 하지 않고 끝냄
	    if (event.getClickCount() != 2)
	        return;
	    
	    // 테이블뷰에서 현재 선택된 ExchangeDTO 객체를 꺼냄
	    MatchingDTO matchingDTO = MatchingTableView.getSelectionModel().getSelectedItem();

	    // 1. FXML 파일을 로딩할 FXMLLoader 객체를 생성 (이 시점에 컨트롤러 객체 "생성"도 준비됨)
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/matching/MatchingDetailPopup.fxml"));
	    Parent popupRoot = loader.load();
    	
	    // 팝업화면 전용 컨트롤러 객체를 받아옴
	    MatchingDetailPopupController popupController = loader.getController();
	    popupController.setMatchingData(matchingDTO); // 팝업 컨트롤러에 데이터 전달 (팝업 라벨에 값 세팅)
	    
	    // 3. 새로운 Stage 생성
	    Stage matchingDetailPopupStage = new Stage();
    	
	    // 4. 모달창으로 만들기(부모창 클릭 차단)
	    matchingDetailPopupStage.initModality(Modality.WINDOW_MODAL);

	    // 5. 팝업창 옵션
	    matchingDetailPopupStage.setTitle("매칭 요청 상세 조회");

	    // 6. 팝업에 Scene 할당, 크기 고정, 팝업창 띄우기
	    matchingDetailPopupStage.setScene(new Scene(popupRoot));
	    matchingDetailPopupStage.setResizable(false);
	    
	    // 7. 팝업창이 닫힐때 까지 대기
	    matchingDetailPopupStage.showAndWait();
	    List<MatchingDTO> result = dao.findReceivedMatchingList(member.getMemberId());
        ObservableList<MatchingDTO> list = FXCollections.observableArrayList(result);
	    MatchingTableView.setItems(list); // 닫히고 나면 테이블 뷰 reload 

    	
    }
}