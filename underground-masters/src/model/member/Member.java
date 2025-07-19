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
	
	public boolean matchesPassword(String password) {
		return this.password.equals(password);
	}
}
