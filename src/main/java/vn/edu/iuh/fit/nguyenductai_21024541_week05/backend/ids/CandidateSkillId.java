package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CandidateSkillId implements Serializable {
    private static final long serialVersionUID = 6578903402086469046L;

    @Column(name = "skill_id", nullable = false)
    private Long skillId;

    @Column(name = "can_id", nullable = false)
    private Long canId;


    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CandidateSkillId entity = (CandidateSkillId) o;
        return Objects.equals(this.skillId, entity.skillId) &&
                Objects.equals(this.canId, entity.canId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillId, canId);
    }

    public CandidateSkillId() {
    }

    public CandidateSkillId(Long skillId, Long canId) {
        this.skillId = skillId;
        this.canId = canId;
    }
}
