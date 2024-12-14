package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;

import java.io.Serializable;

/**
 * DTO for {@link vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.CandidateSkill}
 */
@Value
@Data
@AllArgsConstructor
public class CandidateSkillDTO implements Serializable {
    String moreInfos;
    SkillLevel skillLevel;
}