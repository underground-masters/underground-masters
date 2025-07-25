package model.matching;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/*
 * 작성자: 손윤찬
 */
@Getter
@Setter
public class MatchingDTO {
    private int matchingId;
    private String requestedTalent;
    private String status;
    private Date requestDate;
    private String requesterName;
    private String requesterEmail;
    private String exchangeTitle;
    private String exchangeContent;  
}