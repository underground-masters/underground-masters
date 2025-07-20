package model.member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpDTO {

	private String name;
	private String email;
	private String password;
	private String checkPassword;
	private String phoneNumber;
}
