package model.talent;

import lombok.Getter;
import lombok.Setter;

/*
 * 작성자: 손윤찬
 */
@Getter
@Setter
public class TalentDTO {
    private int talentId;
    private int memberId;
    private String name;
    private String createdAt;
}