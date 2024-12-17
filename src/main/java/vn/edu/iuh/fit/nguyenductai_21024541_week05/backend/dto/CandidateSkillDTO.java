package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;

import java.io.Serializable;

/**
 * DTO for {@link vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.CandidateSkill}
 */
@Value
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandidateSkillDTO implements Serializable {
    CandidateSkillId id;    // ID của CandidateSkill
    Long candidateId;       // ID của Candidate
    Long skillId;           // ID của Skill
    String moreInfos;       // Thông tin thêm
    SkillLevel skillLevel;  // Mức độ kỹ năng
}