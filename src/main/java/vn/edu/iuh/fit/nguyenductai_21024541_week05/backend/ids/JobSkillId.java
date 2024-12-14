package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Job;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class JobSkillId implements Serializable {
    private static final long serialVersionUID = 8432321823861888744L;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof JobSkillId that)) return false;
        return Objects.equals(getJob(), that.getJob()) && Objects.equals(getSkill(), that.getSkill());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getJob(), getSkill());
    }
}
