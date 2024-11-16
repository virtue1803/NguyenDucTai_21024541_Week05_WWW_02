package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillType;

@Getter
@Setter
@Entity
@Table(name = "skill")
@RequiredArgsConstructor
@NoArgsConstructor
public class Skill {
    @Id
    @Column(name = "skill_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skill_description")
    private String skillDescription;

    @Column(name = "skill_name")
    private String skillName;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private SkillType type;

    public Skill(String skillDescription,  String skillName,  SkillType type) {
        this.skillDescription = skillDescription;
        this.skillName = skillName;
        this.type = type;
    }
}