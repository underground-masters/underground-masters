package model.member;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBUtil;

public class MemberDAO {

	private static MemberDAO instance = new MemberDAO();
	
	private MemberDAO() {}
	
	public static MemberDAO getInstance() {
		return instance;
	}
	
	public Member findByEmail(String email) throws ClassNotFoundException, SQLException {
		
		String query = "SELECT * FROM member m WHERE m.email = ?";
		ResultSet resultSet = DBUtil.dbExecuteQuery(query, email);
		
		if(resultSet.next()) {
			Member member = Member.builder()
					.memberId(resultSet.getInt("member_id"))
					.name(resultSet.getString("name"))
					.email(resultSet.getString("email"))
					.password(resultSet.getString("password"))
					.phoneNumber(resultSet.getString("phone_number"))
					.build();
			
			return member;
		}
		
		return null;
	}
	
	public ObservableList<MatchingHistoryDTO> findAllMatchingHistory(Integer memberId) 
			throws ClassNotFoundException, SQLException {
		
		ObservableList<MatchingHistoryDTO> ret = FXCollections.observableArrayList();
		
		String query =
			    "SELECT\n" +
			    "    e.title             AS other_talent_name,\n" +
			    "    m.talent_name       AS my_talent_name,\n" +
			    "    CASE\n" +
			    "        WHEN m.member_id = ? THEN owner.name\n" +
			    "        ELSE requester.name\n" +
			    "    END                 AS other_member_name,\n" +
			    "    CASE\n" +
			    "        WHEN m.member_id = ? THEN owner.phone_number\n" +
			    "        ELSE requester.phone_number\n" +
			    "    END                 AS other_phone_number\n" +
			    "FROM matching m\n" +
			    "    JOIN exchange e ON m.exchange_id = e.exchange_id\n" +
			    "    JOIN member requester ON m.member_id = requester.member_id\n" +
			    "    JOIN member owner ON e.member_id = owner.member_id\n" +
			    "WHERE \n" +
			    "    (m.member_id = ? OR e.member_id = ?)\n" +
			    "    AND m.status = '수락'\n" +
			    "ORDER BY \n" +
			    "    m.matching_id DESC";

		
		ResultSet resultSet = DBUtil.dbExecuteQuery(query, memberId, memberId, memberId, memberId);
		while (resultSet.next()) {
			MatchingHistoryDTO matchingHistoryDto = MatchingHistoryDTO.builder()
					.myTalentName(resultSet.getString("my_talent_name"))
					.otherTalentName(resultSet.getString("other_talent_name"))
					.otherMemberName(resultSet.getString("other_member_name"))
					.otherPhoneNumber(resultSet.getString("other_phone_number"))
					.build();
			
			ret.add(matchingHistoryDto);
		}
		
		return ret;
	}
}
