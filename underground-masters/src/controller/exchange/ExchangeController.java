package controller.exchange;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.exchange.ExchangeDAO;
import model.exchange.ExchangeDTO;

public class ExchangeController implements Initializable {
	
	// ExchangeListView.fxml에서 찾아서 등록
	@FXML private TableView<ExchangeDTO> exchangeTableView;
	@FXML private TableColumn<ExchangeDTO, String> talentName;
	@FXML private TableColumn<ExchangeDTO, String> memberName;
	@FXML private TableColumn<ExchangeDTO, Date> createAt;
	
	// 부서 데이터를 담을 ObservableList -> 테이블 뷰에 넣으려면 ObservableList 써야함 (JavaFX 컨트롤과 자동 동기화가 가능)
    private ObservableList<ExchangeDTO> exchangeList = null;
	
    /**
     * 교환글 목록 조회
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) { // 자동 호출
		
		// 1단계: 컬럼 매핑(바인딩)
		StringProperty memberNameById = new SimpleStringProperty("김경아"); // TODO: memberId로 memberName가져오기
		
		// TableView 컬럼 ↔ DTO 속성(Property) 매핑만 하는 역할
		talentName.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
//		memberName.setCellValueFactory(cellData -> cellData.getValue().exchangeIdProperty().asObject()); //TODO: memberId로 memberName가져오기
		memberName.setCellValueFactory(cellData -> memberNameById);
		createAt.setCellValueFactory(cellData -> cellData.getValue().createAtProperty());
		
		// 2단계: DB에서 데이터 조회 후 TableView에 세팅
	    try {
	        ObservableList<ExchangeDTO> list = ExchangeDAO.findExchangeList();
	        exchangeTableView.setItems(list);
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    
	}
	
	
	// 교환글 목록을 Table View에 매핑하기
	@FXML
	private void populateEmployee(ObservableList<ExchangeDTO> exchangeList) throws ClassNotFoundException {
		exchangeTableView.setItems(exchangeList); // 실제 데이터를 TableView에 set
	}
	

	// 교환글 조회
	@FXML
	private void searchExchanges(ActionEvent event) throws ClassNotFoundException, SQLException {
		exchangeList = ExchangeDAO.findExchangeList(); // DAO에서 DB 쿼리 수행해서 교환글 목록 불러옴
		populateEmployee(exchangeList); // TableView에 보여줌
		
	}
	
	/*
	 * 교환글 등록 팝업창 
	 */
	@FXML
	private void onShowRegisterExchangePopupBtn(ActionEvent event) {
		try {
			// 1. FXML 로드
			Parent popupRoot = FXMLLoader.load(getClass().getResource("/view/exchange/CreateExchangePopup.fxml"));
			
			// 2. 새로운 Stage 생성
			Stage popupStage = new Stage();
			
			// 3. 모달창으로 만들기(부모창 클릭 차단)
			popupStage.initModality(Modality.WINDOW_MODAL);
			
			// 4. 팝업창 옵션
			popupStage.setTitle("교환글 등록");
			
			// 5. Scene 세팅 및 show
			popupStage.setScene(new Scene(popupRoot));
			popupStage.setResizable(false);
			popupStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
