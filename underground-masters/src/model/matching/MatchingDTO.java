package model.matching;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchingDTO {
    private int matchingId;
    private String requestedTalent;
    private String status;
    private String requestDate;
    private String requesterName;
    private String requesterEmail;
    private String exchangeTitle;
    private String exchangeContent;  
    private Date createAt;
}