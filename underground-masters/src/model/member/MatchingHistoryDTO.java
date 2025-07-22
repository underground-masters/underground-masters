package model.member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchingHistoryDTO {

	private String myTalentName;
	private String otherTalentName;
	private String otherMemberName;
	private String otherPhoneNumber;
}
