package model.matching;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchingDTO {
    private int matchingId;             // m.MATCHING_ID
    private String requestedTalent;     // m.TALENT_NAME AS REQUESTED_TALENT
    private String status;              // m.STATUS
    private String requestDate;         // m.CREATE_AT AS REQUEST_DATE
    private String requesterName;       // req.NAME AS REQUESTER_NAME
    private String requesterEmail;      // req.EMAIL AS REQUESTER_EMAIL
    private String exchangeTitle;       // e.TITLE AS EXCHANGE_TITLE
    private String exchangeContent;     // e.CONTENT AS EXCHANGE_CONTENT
}