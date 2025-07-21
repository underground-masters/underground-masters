package model.exchange;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.border.TitledBorder;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBUtil;

public class ExchangeDAO {
	
	/**
	 * 교환글 목록 조회
	 */
	public static ObservableList<ExchangeDTO> findExchangeList() throws ClassNotFoundException, SQLException {
		ObservableList<ExchangeDTO> exchangeList = FXCollections.observableArrayList(); // 교환글 담을 리스트
		
		// DB에서 교환글 리스트 불러오기
		String selectStmt = "SELECT TITLE, CONTENT, MEMBER_ID, CREATE_AT FROM MASTERS.EXCHANGE";
		ResultSet resultSet = DBUtil.dbExecuteQuery(selectStmt);
		
		// resultSet에 담겨있는 교환글 리스트를 exchangeList에 담기
		exchangeList = getExchangeDTOFromResultSet(resultSet);
		
		return exchangeList;
	}
	
	// ResultSet 한 행을 ExchangeDTO 객체로 변환
	public static ObservableList<ExchangeDTO> getExchangeDTOFromResultSet(ResultSet resultSet) throws SQLException {
		ObservableList<ExchangeDTO> exchangeList = FXCollections.observableArrayList(); // 교환글 담을 리스트

		while (resultSet.next()) { // resultSet 순회하면서 exchangeDTO로 담기
			ExchangeDTO exchangeDTO = ExchangeDTO.builder()
					.title(new SimpleStringProperty(resultSet.getString("TITLE")))
					.content(new SimpleStringProperty(resultSet.getString("CONTENT")))
					.memberId(new SimpleIntegerProperty(resultSet.getInt("MEMBER_ID")))
					.createAt(new SimpleObjectProperty<>(resultSet.getDate("CREATE_AT")))
					.build();
			
			exchangeList.add(exchangeDTO); // ObservableList에 담기
		}
		return exchangeList;
	}
	
	/**
	 * 재능 리스트 불러오기
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static ObservableList<String> findTalentList() throws ClassNotFoundException, SQLException {
		ObservableList<String> talentList = FXCollections.observableArrayList(); // 내가 보유하고 있는 재능을 담을 리스트
		
		int memberId = 2; // TODO: 임시 데이터(현재 접속한 ID값으로 바꾸기)
		
		// DB에서 재능 리스트 불러오기
		String selectStmt = "SELECT NAME FROM MASTERS.TALENT WHERE MEMBER_ID = ?";
		ResultSet resultSet = DBUtil.dbExecuteQuery(selectStmt, memberId);
		
		// resultSet에 담겨있는 재능 리스트를 talentList에 담기
		talentList = getTalentDTOFromResultSet(resultSet); 
		
		return talentList;
	}
	
	// ResultSet 한 행을 TalentDTO 객체로 변환
	public static ObservableList<String> getTalentDTOFromResultSet(ResultSet resultSet) throws SQLException {
		ObservableList<String> talentList = FXCollections.observableArrayList(); // 내가 보유하고 있는 재능을 담을 리스트
		
		String talentName = ""; // 재능 이름
		while (resultSet.next()) {
			talentName = resultSet.getString("NAME");
			
			talentList.add(talentName); // ObservableList에 담기
		}
		return talentList;
	}
	
	/**
	 * 교환글 등록
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void createExchange(String selectedTalent, String description) throws ClassNotFoundException, SQLException {
		String insertStmt = "{ call create_exchange(?, ?, ?) }"; // 재능, 상세설명, 멤버id
		int memberId = 2; //TODO: 임시 데이터(현재 접속한 ID값으로 바꾸기)
		
		// DB에 교환글 등록하기
		DBUtil.dbExecuteUpdate(insertStmt, selectedTalent, description, memberId);
	}
}
