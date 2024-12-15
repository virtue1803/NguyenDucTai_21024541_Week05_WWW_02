package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CandidateSkillId implements Serializable {
    private static final long serialVersionUID = 6578903402086469046L;

    @ManyToOne
    @JoinColumn(name = "can_id", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CandidateSkillId that)) return false;
        return Objects.equals(getCandidate(), that.getCandidate()) && Objects.equals(getSkill(), that.getSkill());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCandidate(), getSkill());
    }
}
