package model.matching;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.AuthenticationSession;
import util.DBUtil;

public class MatchingDAO {

    /**
     * 받은 매칭 요청 목록 조회 (내가 등록한 교환글에 들어온 요청들)
     */
    public List<MatchingDTO> findReceivedMatchingList(int memberId) throws SQLException, ClassNotFoundException {
        String sql =
            "SELECT " +
            "       m.MATCHING_ID,  " +
            "       m.TALENT_NAME AS REQUESTED_TALENT, " +
            "       m.STATUS, " +
            "       m.CREATE_AT AS REQUEST_DATE, " +
            "       req.NAME AS REQUESTER_NAME, " +
            "       req.EMAIL AS REQUESTER_EMAIL, " +
            "       e.TITLE AS EXCHANGE_TITLE, " +
            "       e.CONTENT AS EXCHANGE_CONTENT " +
            "FROM MATCHING m " +
            "JOIN EXCHANGE e ON m.EXCHANGE_ID = e.EXCHANGE_ID " +
            "JOIN MEMBER req ON m.MEMBER_ID = req.MEMBER_ID " +
            "WHERE e.MEMBER_ID = ? " +
            "  AND e.IS_DELETE = 'N' " +
            "ORDER BY m.CREATE_AT DESC";

        ResultSet rs = DBUtil.dbExecuteQuery(sql, memberId);
        return parseMatchingResultSet(rs);
    }

    /**
     * 보낸 매칭 요청 목록 조회 (내가 다른 사람 글에 신청한 것)
     */
    public List<MatchingDTO> findSendMatchingList(int memberId) throws SQLException, ClassNotFoundException {
        String sql =
            "SELECT " +
            "       m.MATCHING_ID, " +
            "       m.TALENT_NAME AS REQUESTED_TALENT, " +
            "       m.STATUS, " +
            "       m.CREATE_AT AS REQUEST_DATE, " +
            "       owner.NAME AS REQUESTER_NAME, " +
            "       owner.EMAIL AS REQUESTER_EMAIL, " +
            "       e.TITLE AS EXCHANGE_TITLE, " +
            "       e.CONTENT AS EXCHANGE_CONTENT " +
            "FROM MATCHING m " +
            "JOIN EXCHANGE e ON m.EXCHANGE_ID = e.EXCHANGE_ID " +
            "JOIN MEMBER owner ON e.MEMBER_ID = owner.MEMBER_ID " +
            "WHERE m.MEMBER_ID = ? " +
            "  AND e.IS_DELETE = 'N' " +
            "ORDER BY m.CREATE_AT DESC";

        ResultSet rs = DBUtil.dbExecuteQuery(sql, memberId);
        return parseMatchingResultSet(rs);
    }



    /**
     * 공통 ResultSet 파싱 메서드
     */
    private List<MatchingDTO> parseMatchingResultSet(ResultSet rs) throws SQLException {
        List<MatchingDTO> list = new ArrayList<>();

        while (rs.next()) {
            MatchingDTO dto = new MatchingDTO();
            dto.setMatchingId(rs.getInt("MATCHING_ID"));
            dto.setRequestedTalent(rs.getString("REQUESTED_TALENT"));
            dto.setStatus(rs.getString("STATUS"));
            dto.setRequestDate(rs.getString("REQUEST_DATE"));
            dto.setRequesterName(rs.getString("REQUESTER_NAME"));
            dto.setRequesterEmail(rs.getString("REQUESTER_EMAIL"));
            dto.setExchangeTitle(rs.getString("EXCHANGE_TITLE"));
            dto.setExchangeContent(rs.getString("EXCHANGE_CONTENT"));

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
		int memberId = AuthenticationSession.getInstance().getMember().getMemberId();
		
		// DB에 교환글 등록하기
		DBUtil.dbExecuteUpdate(insertStmt, exchangeId, talentName, memberId);
	}
	//매칭 수락, 거절
	public void updateMatchingStatus(int matchingId, String newStatus) throws SQLException, ClassNotFoundException {
	    String sql = "UPDATE MATCHING SET STATUS = ? WHERE MATCHING_ID = ?";
	    DBUtil.dbExecuteUpdate(sql, newStatus, matchingId);
	}
}