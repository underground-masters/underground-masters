package model.matching;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.exchange.ExchangeDTO;
import util.DBUtil;

public class MatchingDAO {

    /**
     * 받은 매칭 요청 목록 조회 (status = '대기')
     */
    public List<MatchingDTO> findReceivedMatchingList(int memberId) throws SQLException, ClassNotFoundException {
        List<MatchingDTO> list = new ArrayList<>();

        String sql = 
        	    "SELECT t.name AS talent_name, " +
        	    "       m.name AS member_name, " +
        	    "       g.status, " +
        	    "       TO_CHAR(g.create_at, 'YYYY-MM-DD') AS request_date " +
        	    "FROM matching g " +
        	    "JOIN exchange e ON g.exchange_id = e.exchange_id " +
        	    "JOIN member m ON g.member_id = m.member_id " +
        	    "JOIN talent t ON e.talent_id = t.talent_id " +
        	    "WHERE e.member_id = ? AND g.status = '대기'";


        ResultSet rs = DBUtil.dbExecuteQuery(sql, memberId);

        while (rs.next()) {
            MatchingDTO dto = new MatchingDTO();
            dto.setTalentName(rs.getString("talent_name"));
            dto.setMemberName(rs.getString("member_name"));
            dto.setStatus(rs.getString("status"));
            dto.setRequestDate(rs.getString("request_date"));

            list.add(dto);
        }

        return list;
    }
    
	/**
	 * 매칭 등록 메서드
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void createMatching(int exchangeId, String talentName) throws ClassNotFoundException, SQLException {
		String insertStmt = "{ call create_matching(?, ?, ?) }"; // exchangeId, talentName, memberId로 저장
		int memberId = 1; //TODO: 임시 데이터(현재 접속한 ID값으로 바꾸기)

		// DB에 교환글 등록하기
		DBUtil.dbExecuteUpdate(insertStmt, exchangeId, talentName, memberId);
	}
}