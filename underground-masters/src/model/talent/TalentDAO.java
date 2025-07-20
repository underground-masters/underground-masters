package model.talent;

import java.sql.*;
import java.util.*;
import util.DBUtil;

public class TalentDAO {

    public List<TalentDTO> findTalentListByMemberId(int memberId) {
        List<TalentDTO> list = new ArrayList<>();
        String sql = "SELECT talent_id, member_id, name, TO_CHAR(create_at, 'YYYY.MM.DD') AS create_at " +
                     "FROM talent WHERE member_id = ? AND is_delete = 'N' ORDER BY create_at DESC";

        try (ResultSet rs = DBUtil.dbExecuteQuery(sql, memberId)) {
            while (rs.next()) {
                TalentDTO dto = new TalentDTO();
                dto.setTalentId(rs.getInt("talent_id"));
                dto.setMemberId(rs.getInt("member_id"));
                dto.setName(rs.getString("name"));
                dto.setCreatedAt(rs.getString("create_at"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteTalent(int talentId) {
        String sql = "CALL DELETE_TALENT(?)";
        try {
            DBUtil.dbExecuteUpdate(sql, talentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}