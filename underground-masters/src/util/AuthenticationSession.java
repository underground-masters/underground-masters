package util;

import java.sql.SQLException;

import model.member.Member;
import model.member.MemberDAO;

/*
 * 작성자: 정의탁
 */
public class AuthenticationSession {
	
	private static AuthenticationSession instance = new AuthenticationSession();
    private Member member;

    private AuthenticationSession() {}

    public static AuthenticationSession getInstance() {
        return instance;
    }

    public Member getMember() {
        return member;
    }
    
    public void setMember(Member member) {
    	this.member = member;
    }

    public boolean isAuthenticated() {
        return member != null;
    }
    
    public void reload(MemberDAO memberDao) throws ClassNotFoundException, SQLException {
    	setMember(memberDao.findByEmail(this.member.getEmail()));
    }
}
