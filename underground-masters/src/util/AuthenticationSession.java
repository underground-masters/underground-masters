package util;

import model.member.Member;

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
}
