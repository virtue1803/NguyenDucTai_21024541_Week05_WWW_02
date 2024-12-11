package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillType;

@Getter
@Setter
@Entity
@Table(name = "skill")
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @Column(name = "skill_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skill_description")
    private String skillDescription;

    @Column(name = "skill_name")
    private String skillName;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SkillType type;

    public Skill(String skillDescription, @NonNull String skillName, @NonNull SkillType type) {
        this.skillDescription = skillDescription;
        this.skillName = skillName;
        this.type = type;
    }
}
