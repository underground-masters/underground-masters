package model.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {

	private Integer memberId;
	private String name;
	private String email;
	private String password;
	private String phoneNumber;
	
	// 패스워드가 일치하면, true 반환.
	public boolean matchesPassword(String password) {
		return this.password.equals(password);
	}
}
