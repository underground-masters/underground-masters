package controller.exchange;

import java.io.IOException;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.exchange.ExchangeDAO;
import model.exchange.ExchangeDTO;

public class ExchangeController implements Initializable {
	
	private final ExchangeDAO exchangeDAO = new ExchangeDAO(); // 공유
	
	// ExchangeListView.fxml에서 찾아서 등록
	@FXML private TableView<ExchangeDTO> exchangeTableView; // TableView
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
	        ObservableList<ExchangeDTO> list = exchangeDAO.findExchangeList();
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
		exchangeList = exchangeDAO.findExchangeList(); // DAO에서 DB 쿼리 수행해서 교환글 목록 불러옴
		populateEmployee(exchangeList); // TableView에 보여줌
		
	}
	
	/**
	 * 테이블뷰의 행을 더블클릭했을 때 팝업창을 띄워 교환글 상세를 보여주는 메서드
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
	    ExchangeDTO exchangeDTO = exchangeTableView.getSelectionModel().getSelectedItem();
	    
	    
	    String directory = "";
	    int memberId = 1; //TODO

	    if (memberId == exchangeDTO.getMemberId().get()) { // 현재 로그인한 ID와 더블클릭한 데이터의 member ID가 같으면 MyExchangeDetailPopup 열기
	    	directory = "/view/exchange/MyExchangeDetailPopup.fxml";
	    } else { // 다르면 NotMyExchangeDetailPopup 열기
	    	directory = "/view/exchange/NotMyExchangeDetailPopup.fxml";
	    }
	    
	    
	    // 1. FXML 파일을 로딩할 FXMLLoader 객체를 생성 (이 시점에 컨트롤러 객체 "생성"도 준비됨)
	    FXMLLoader loader = new FXMLLoader(getClass().getResource(directory));
	    Parent popupRoot = loader.load();

	    // 팝업화면 전용 컨트롤러 객체를 받아옴
	    ExchangeDetailPopupController popupController = loader.getController();
	    popupController.setExchangeData(exchangeDTO); // 팝업 컨트롤러에 데이터 전달 (팝업 라벨에 값 세팅)
	    
	    
	    // 2. 새로운 Stage 생성
	    Stage exchangeDetailPopupStage = new Stage();

	    // 3. 모달창으로 만들기(부모창 클릭 차단)
	    exchangeDetailPopupStage.initModality(Modality.WINDOW_MODAL);

	    // 4. 팝업창 옵션
	    exchangeDetailPopupStage.setTitle("교환글 상세 조회");

	    // 5. 팝업에 Scene 할당, 크기 고정, 팝업창 띄우기
	    exchangeDetailPopupStage.setScene(new Scene(popupRoot));
	    exchangeDetailPopupStage.setResizable(false);
	    
	    // 6. 팝업창이 닫힐때 까지 대기
	    exchangeDetailPopupStage.showAndWait();
	    exchangeTableView.setItems(exchangeDAO.findExchangeList()); // 닫히고 나면 테이블 뷰 reload
	}
	
	/*
	 * 교환글 등록 팝업창 
	 */
	@FXML
	private void onShowRegisterExchangePopupBtn(ActionEvent event) {
		try {
			// 1. FXML 파일을 로딩할 FXMLLoader 객체를 생성(fxml 등록)
			Parent popupRoot = FXMLLoader.load(getClass().getResource("/view/exchange/CreateExchangePopup.fxml"));
			
			// 2. 새로운 Stage 생성
			Stage createExchangePopupStage = new Stage();
			
			// 3. 모달창으로 만들기(부모창 클릭 차단)
			createExchangePopupStage.initModality(Modality.WINDOW_MODAL);
			
			// 4. 팝업창 옵션
			createExchangePopupStage.setTitle("교환글 등록");
			
			// 5. Scene 세팅 및 show
			createExchangePopupStage.setScene(new Scene(popupRoot));
			createExchangePopupStage.setResizable(false);
			createExchangePopupStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
