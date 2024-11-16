package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id", nullable = false)
    private Long id;

    @Column(name = "skill_description")
    private String skillDescription;

    @Column(name = "skill_name")
    private String skillName;

    @Column(name = "type")
    private Byte type;

    public Skill() {
    }

    public Skill(String skillDescription, String skillName, Byte type) {
        this.skillDescription = skillDescription;
        this.skillName = skillName;
        this.type = type;
    }
}