package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.JobSkillId;

@Getter
@Setter
@Entity
@Table(name = "job_skill")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class JobSkill {
    @EmbeddedId
    private JobSkillId id;

    @Column(name = "more_infos", length = 1000)
    private String moreInfos;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "skill_level", nullable = false)
    private SkillLevel skillLevel;
}