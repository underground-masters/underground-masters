package model.member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDTO {

	private Integer memberId;
	private String name;
	private String email;
	private String password;
	private String phoneNumber;
}
