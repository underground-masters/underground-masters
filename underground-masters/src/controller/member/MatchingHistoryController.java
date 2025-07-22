package controller.member;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import controller.common.NavbarController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.member.MatchingHistoryDTO;
import model.member.Member;
import model.member.MemberDAO;
import util.AuthenticationSession;

public class MatchingHistoryController extends NavbarController implements Initializable {

	private Member member;
	private MemberDAO memberDao;
	
	@FXML private TableView<MatchingHistoryDTO> matchingHistoryTableView;
	@FXML private TableColumn<MatchingHistoryDTO, String> myTalentName;
	@FXML private TableColumn<MatchingHistoryDTO, String> otherTalentName;
	@FXML private TableColumn<MatchingHistoryDTO, String> otherMemberName;
	@FXML private TableColumn<MatchingHistoryDTO, String> otherPhoneNumber;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		member = AuthenticationSession.getInstance().getMember();		
		memberDao = MemberDAO.getInstance();
		
		// TableView 컬럼 ↔ DTO 속성(Property) 매핑만 하는 역할
		myTalentName.setCellValueFactory(new PropertyValueFactory<>("myTalentName"));
		otherTalentName.setCellValueFactory(new PropertyValueFactory<>("otherTalentName"));
		otherMemberName.setCellValueFactory(new PropertyValueFactory<>("otherMemberName"));
		otherPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("otherPhoneNumber"));
		
		try {
			ObservableList<MatchingHistoryDTO> matchingHistoryDtoList = memberDao.findAllMatchingHistory(member.getMemberId());			
			matchingHistoryTableView.setItems(matchingHistoryDtoList);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
}
