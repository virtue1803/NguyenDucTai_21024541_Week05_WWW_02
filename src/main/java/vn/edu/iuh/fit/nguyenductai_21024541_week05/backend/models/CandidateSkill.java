package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;

@Getter
@Setter
@Entity
@Table(name = "candidate_skill")
public class CandidateSkill {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private CandidateSkillId id;

    @MapsId("skillId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @MapsId("canId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "can_id", nullable = false)
    private Candidate can;

    @Column(name = "more_infos", length = 1000)
    private String moreInfos;

    @Column(name = "skill_level", nullable = false)
    private Byte skillLevel;

    public CandidateSkill() {
    }

    public CandidateSkill(CandidateSkillId id, Skill skill, Candidate can, String moreInfos, Byte skillLevel) {
        this.id = id;
        this.skill = skill;
        this.can = can;
        this.moreInfos = moreInfos;
        this.skillLevel = skillLevel;
    }
}