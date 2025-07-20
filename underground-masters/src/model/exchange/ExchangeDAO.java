package model.exchange;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBUtil;

public class ExchangeDAO {
	/**
	 * 재능 리스트 불러오기
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static ObservableList<String> findTalentList() throws ClassNotFoundException, SQLException {
		ObservableList<String> talentList = FXCollections.observableArrayList(); // 내가 보유하고 있는 재능을 담을 리스트
		
		int memberId = 1; // TODO: 임시 데이터(현재 접속한 ID값으로 바꾸기)
		String selectStmt = "SELECT NAME FROM MASTERS.TALENT WHERE MEMBER_ID = ?";
		ResultSet resultSet = DBUtil.dbExecuteQuery(selectStmt, memberId); // DB에서 재능 리스트 불러오기
		
		talentList = getTalentDTOFromResultSet(resultSet); // resultSet에 담겨있는 재능 리스트를 talentList에 담기
		
		return talentList;
	}
	
	/**
	 * ResultSet 한 행을 TalentDTO 객체로 변환 
	 * @throws SQLException 
	 */
	public static ObservableList<String> getTalentDTOFromResultSet(ResultSet resultSet) throws SQLException {
		ObservableList<String> talentList = FXCollections.observableArrayList(); // 내가 보유하고 있는 재능을 담을 리스트
		String name = "";
		while (resultSet.next()) {
			name = resultSet.getString("NAME");
			talentList.add(name);
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
		int memberId = 1; //TODO: 임시 데이터(현재 접속한 ID값으로 바꾸기)
		DBUtil.dbExecuteUpdate(insertStmt, selectedTalent, description, memberId);
	}
}
