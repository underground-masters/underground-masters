package model.exchange;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.AuthenticationSession;
import util.DBUtil;

public class ExchangeDAO {
	
	/**
	 * 교환글 목록 조회 메서드
	 */
	public ObservableList<ExchangeDTO> findExchangeList() throws ClassNotFoundException, SQLException {
		ObservableList<ExchangeDTO> exchangeList = FXCollections.observableArrayList(); // 교환글 담을 리스트
		
		// DB에서 교환글 목록 불러오기
		String selectStmt = "SELECT * FROM MASTERS.EXCHANGE WHERE IS_DELETE='N'";
		ResultSet resultSet = DBUtil.dbExecuteQuery(selectStmt);
		
		// resultSet에 담겨있는 교환글 목록을 exchangeList에 담기
		exchangeList = getExchangeDTOFromResultSet(resultSet);
		
		return exchangeList;
	}
	
	// ResultSet 한 행을 ExchangeDTO 객체로 변환
	public ObservableList<ExchangeDTO> getExchangeDTOFromResultSet(ResultSet resultSet) throws SQLException {
		ObservableList<ExchangeDTO> exchangeList = FXCollections.observableArrayList(); // 교환글 담을 리스트

		while (resultSet.next()) { // resultSet 순회하면서 exchangeDTO로 담기
			ExchangeDTO exchangeDTO = ExchangeDTO.builder()
					.exchangeId(new SimpleIntegerProperty(resultSet.getInt("EXCHANGE_ID")))
					.title(new SimpleStringProperty(resultSet.getString("TITLE")))
					.content(new SimpleStringProperty(resultSet.getString("CONTENT")))
					.memberId(new SimpleIntegerProperty(resultSet.getInt("MEMBER_ID")))
					.createAt(new SimpleObjectProperty<>(resultSet.getDate("CREATE_AT")))
					.isDelete(new SimpleStringProperty(resultSet.getString("IS_DELETE")))
					.build();
			
			exchangeList.add(exchangeDTO); // ObservableList에 담기
		}
		return exchangeList;
	}
	
	/**
	 * 재능 목록 조회 메서드
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public ObservableList<String> findTalentList() throws ClassNotFoundException, SQLException {
		ObservableList<String> talentList = FXCollections.observableArrayList(); // 내가 보유하고 있는 재능을 담을 리스트
		
		int memberId = AuthenticationSession.getInstance().getMember().getMemberId();
		
		// DB에서 재능 목록 불러오기
		String selectStmt = "SELECT NAME FROM MASTERS.TALENT WHERE MEMBER_ID = ?";
		ResultSet resultSet = DBUtil.dbExecuteQuery(selectStmt, memberId);
		
		// resultSet에 담겨있는 재능 목록을 talentList에 담기
		talentList = getTalentDTOFromResultSet(resultSet); 
		
		return talentList;
	}
	
	// ResultSet 한 행을 TalentDTO 객체로 변환
	public ObservableList<String> getTalentDTOFromResultSet(ResultSet resultSet) throws SQLException {
		ObservableList<String> talentList = FXCollections.observableArrayList(); // 내가 보유하고 있는 재능을 담을 리스트
		
		String talentName = ""; // 재능 이름
		while (resultSet.next()) {
			talentName = resultSet.getString("NAME");
			
			talentList.add(talentName); // ObservableList에 담기
		}
		return talentList;
	}
	
	/**
	 * 교환글 등록 메서드
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void createExchange(String title, String description) throws ClassNotFoundException, SQLException {
		String insertStmt = "{ call create_exchange(?, ?, ?) }"; // title(재능명), description, member_id로 저장
		int memberId = AuthenticationSession.getInstance().getMember().getMemberId();
		
		// DB에 교환글 등록하기
		DBUtil.dbExecuteUpdate(insertStmt, title, description, memberId);
	}
	
	/**
	 * 교환글 삭제 메서드
	 * (UPDATE : IS_DELETE: 'N' -> 'Y')
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void deleteExchange(int exchangeId) throws ClassNotFoundException, SQLException {
		String deleteStmt = "{ call delete_exchange(?) }";
		
		// DB에서 교환글 삭제하기
		DBUtil.dbExecuteUpdate(deleteStmt, exchangeId);
	}
}
