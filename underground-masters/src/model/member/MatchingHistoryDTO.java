package model.member;

import lombok.Builder;
import lombok.Data;

/**
 * 작성자: 정의탁
 */

@Data
@Builder
public class MatchingHistoryDTO {

	private String myTalentName;
	private String otherTalentName;
	private String otherMemberName;
	private String otherPhoneNumber;
}
