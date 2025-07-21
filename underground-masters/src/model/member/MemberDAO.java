package model.member;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	
}
