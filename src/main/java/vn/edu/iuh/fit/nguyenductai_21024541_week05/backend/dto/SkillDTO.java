package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillType;

import java.io.Serializable;

/**
 * DTO for {@link vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill}
 */
@Value
@Data
@AllArgsConstructor
public class SkillDTO implements Serializable {
    Long id;
    String skillDescription;
    String skillName;
    SkillType type;
}