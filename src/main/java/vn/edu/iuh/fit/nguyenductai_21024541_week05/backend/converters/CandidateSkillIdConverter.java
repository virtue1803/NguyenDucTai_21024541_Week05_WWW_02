package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.converters;



import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;

@Component
public class CandidateSkillIdConverter implements Converter<String, CandidateSkillId> {

    @Override
    public CandidateSkillId convert(String source) {
        if (source == null || !source.contains("-")) {
            throw new IllegalArgumentException("Invalid format for CandidateSkillId. Expected 'candidateId-skillId'.");
        }

        // Phân tách chuỗi
        String[] parts = source.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid format for CandidateSkillId. Expected 'candidateId-skillId'.");
        }

        try {
            // Lấy candidateId và skillId từ chuỗi
            Long candidateId = Long.parseLong(parts[0]);
            Long skillId = Long.parseLong(parts[1]);

            // Tạo các thực thể Candidate và Skill từ ID
            Candidate candidate = new Candidate();
            candidate.setId(candidateId);

            Skill skill = new Skill();
            skill.setId(skillId);

            // Trả về CandidateSkillId mới
            return new CandidateSkillId(candidate, skill);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in CandidateSkillId.", e);
        }
    }
}
