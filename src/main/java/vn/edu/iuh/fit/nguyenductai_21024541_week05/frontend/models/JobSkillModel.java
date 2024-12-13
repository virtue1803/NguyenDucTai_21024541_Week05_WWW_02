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
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.JobSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.JobSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
public class JobSkillModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api/job-skills";  // URL của API JobSkill

    @Autowired
    public JobSkillModel(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.mapper.registerModule(new JavaTimeModule());  // Để xử lý dữ liệu thời gian
    }

    // Thêm một JobSkill
    public Response insert(JobSkill jobSkill) {
        URI uri = URI.create(baseUrl);
        return restTemplate.postForObject(uri, jobSkill, Response.class);
    }

    // Thêm nhiều JobSkill
    public Response insertAll(List<JobSkill> jobSkills) {
        URI uri = URI.create(baseUrl + "/bulk");
        return restTemplate.postForObject(uri, jobSkills, Response.class);
    }

    // Cập nhật một JobSkill
    public Response update(JobSkillId id, JobSkill jobSkill) {
        URI uri = URI.create(baseUrl + "/" + id.getJob().getId());  // Sửa đổi để truy cập ID từ Job
        restTemplate.put(uri, jobSkill);
        return new Response(HttpStatus.OK.value(), "Job Skill updated successfully", jobSkill);
    }

    // Xóa một JobSkill
    public void delete(JobSkillId id) {
        URI uri = URI.create(baseUrl + "/" + id.getJob().getId());  // Sửa đổi để truy cập ID từ Job
        restTemplate.delete(uri);
    }

    // Lấy một JobSkill theo ID
    public Optional<JobSkill> getById(JobSkillId id) {
        URI uri = URI.create(baseUrl + "/" + id.getJob().getId());  // Sửa đổi để truy cập ID từ Job
        return Optional.ofNullable(restTemplate.getForObject(uri, JobSkill.class));
    }

    // Lấy tất cả JobSkill
    public List<JobSkill> getAll() {
        URI uri = URI.create(baseUrl);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<JobSkill>>() {}).getBody();
    }

    // Tìm kiếm JobSkill theo Skill ID
    public List<JobSkill> getBySkillId(Long skillId) {
        URI uri = URI.create(baseUrl + "/by-skill/" + skillId);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<JobSkill>>() {}).getBody();
    }

    // Tìm kiếm JobSkill theo Job ID
    public List<JobSkill> getByJobId(Long jobId) {
        URI uri = URI.create(baseUrl + "/by-job/" + jobId);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<JobSkill>>() {}).getBody();
    }

    // Tìm kiếm JobSkill theo Skill Level
    public List<JobSkill> getBySkillLevel(SkillLevel skillLevel) {
        URI uri = URI.create(baseUrl + "/by-skill-level/" + skillLevel.name());
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<JobSkill>>() {}).getBody();
    }
}
