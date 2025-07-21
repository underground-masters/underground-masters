package model.talent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TalentDTO {
    private int talentId;
    private int memberId;
    private String name;
    private String createdAt;
}