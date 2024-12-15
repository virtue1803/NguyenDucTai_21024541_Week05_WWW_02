package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;

@Getter
@Setter
@Entity
@Table(name = "candidate_skill")
@NoArgsConstructor
@AllArgsConstructor
public class CandidateSkill {

    @EmbeddedId
    private CandidateSkillId id;

    @Column(name = "more_infos", length = 1000)
    private String moreInfos;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_level", nullable = false)
    private SkillLevel skillLevel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "can_id", insertable = false, updatable = false)
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "skill_id", insertable = false, updatable = false)
    private Skill skill;


}

