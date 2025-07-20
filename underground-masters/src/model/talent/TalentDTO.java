package model.talent;

public class TalentDTO {
    private int talentId;
    private int memberId;
    private String name;
    private String createdAt;

    public TalentDTO() {}

    public TalentDTO(int talentId, int memberId, String name, String createdAt) {
        this.talentId = talentId;
        this.memberId = memberId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public int getTalentId() {
        return talentId;
    }

    public void setTalentId(int talentId) {
        this.talentId = talentId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}