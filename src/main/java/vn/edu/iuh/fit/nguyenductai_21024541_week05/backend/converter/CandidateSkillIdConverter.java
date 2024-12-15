package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.CandidateService;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.SkillService;

@Component
public class CandidateSkillIdConverter implements Converter<String, CandidateSkillId> {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private SkillService skillService;

    @Override
    public CandidateSkillId convert(String source) {
        try {
            // Phân tách chuỗi theo dấu phẩy (",") thay vì "/"
            String[] parts = source.split(",");
            Long candidateId = Long.parseLong(parts[0]);
            Long skillId = Long.parseLong(parts[1]);

            // Tìm kiếm Candidate và Skill từ cơ sở dữ liệu
            Candidate candidate = candidateService.getById(candidateId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Candidate ID"));

            Skill skill = skillService.getById(skillId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Skill ID"));

            // Tạo CandidateSkillId từ đối tượng Candidate và Skill
            return new CandidateSkillId(candidate, skill);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid CandidateSkillId format. Expected format: candidateId,skillId");
        }
    }
}