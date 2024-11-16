package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.JobSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.JobSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;

import java.net.URI;
import java.util.List;

@Component
public class JobSkillModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUri = "http://localhost:8080/api/job-skill/";

    public JobSkillModel() {
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.restTemplate = new RestTemplate();
    }

    // Insert a new JobSkill
    public JobSkill insert(JobSkill jobSkill) {
        Response response = restTemplate.postForObject(URI.create(baseUri), jobSkill, Response.class);
        return mapper.convertValue(response.getData(), JobSkill.class);
    }

    // Insert multiple JobSkills
    public List<JobSkill> insertAll(List<JobSkill> jobSkills) {
        Response response = restTemplate.postForObject(URI.create(baseUri + "list"), jobSkills, Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<JobSkill>>() {});
    }

    // Update a JobSkill
    public JobSkill update(JobSkillId jobSkillId, JobSkill jobSkill) {
        restTemplate.put(URI.create(baseUri + jobSkillId), jobSkill);
        return getById(jobSkillId);
    }

    // Delete a JobSkill
    public void delete(JobSkillId jobSkillId) {
        restTemplate.delete(URI.create(baseUri + jobSkillId));
    }

    // Get a JobSkill by ID
    public JobSkill getById(JobSkillId jobSkillId) {
        Response response = restTemplate.getForObject(URI.create(baseUri + jobSkillId), Response.class);
        return mapper.convertValue(response.getData(), JobSkill.class);
    }

    // Get all JobSkills
    public List<JobSkill> getAll() {
        Response response = restTemplate.getForObject(URI.create(baseUri), Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<JobSkill>>() {});
    }

    // Get all Jobs by Skill ID
    public List<?> getAllJobsBySkill(Long skillId) {
        Response response = restTemplate.getForObject(URI.create(baseUri + "jobs/" + skillId), Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<?>>() {});
    }
}
