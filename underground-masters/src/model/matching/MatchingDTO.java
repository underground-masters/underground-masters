package model.matching;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchingDTO {
    private String talentName;     // 상대 재능
    private String memberName;     // 상대 고수
    private String status;         // 상태 (대기, 수락, 거절)
    private String requestDate;    // 신청 날짜
}