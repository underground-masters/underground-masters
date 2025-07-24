package model.member;

import lombok.Builder;
import lombok.Data;

/**
 * 작성자: 정의탁
 */

@Data
@Builder
public class MemberDTO {

	private Integer memberId;
	private String name;
	private String email;
	private String password;
	private String phoneNumber;
}
