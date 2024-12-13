package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.CandidateSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
public class CandidateSkillModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api/candidate-skills";  // URL của API CandidateSkill

    @Autowired
    public CandidateSkillModel(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.mapper.registerModule(new JavaTimeModule());  // Để xử lý dữ liệu thời gian
    }

    // Phương thức thêm một CandidateSkill
    public Response insert(CandidateSkill candidateSkill) {
        URI uri = URI.create(baseUrl);
        return restTemplate.postForObject(uri, candidateSkill, Response.class);
    }

    // Phương thức thêm nhiều CandidateSkill
    public Response insertAll(List<CandidateSkill> candidateSkills) {
        URI uri = URI.create(baseUrl + "/bulk");
        return restTemplate.postForObject(uri, candidateSkills, Response.class);
    }

    // Phương thức cập nhật một CandidateSkill
    public Response update(CandidateSkillId id, CandidateSkill candidateSkill) {
        URI uri = URI.create(baseUrl + "/" + id.getCandidate().getId());
        restTemplate.put(uri, candidateSkill);
        return new Response(HttpStatus.OK.value(), "Skill updated successfully", candidateSkill);
    }

    // Phương thức xóa một CandidateSkill
    public void delete(CandidateSkillId id) {
        URI uri = URI.create(baseUrl + "/" + id.getCandidate().getId());
        restTemplate.delete(uri);
    }

    // Phương thức lấy CandidateSkill theo ID
    public Optional<CandidateSkill> getById(CandidateSkillId id) {
        URI uri = URI.create(baseUrl + "/" + id.getCandidate().getId());
        return Optional.ofNullable(restTemplate.getForObject(uri, CandidateSkill.class));
    }

    // Phương thức lấy tất cả CandidateSkill
    public List<CandidateSkill> getAll() {
        URI uri = URI.create(baseUrl);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<CandidateSkill>>() {}).getBody();
    }

    // Phương thức tìm kiếm CandidateSkill theo CandidateId
    public List<CandidateSkill> getSkillsByCandidateId(Long candidateId) {
        URI uri = URI.create(baseUrl + "/candidate/" + candidateId);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<CandidateSkill>>() {}).getBody();
    }

    // Phương thức tìm kiếm CandidateSkill theo SkillLevel
    public List<CandidateSkill> getSkillsByLevel(SkillLevel skillLevel) {
        URI uri = URI.create(baseUrl + "/level/" + skillLevel);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<CandidateSkill>>() {}).getBody();
    }
}
