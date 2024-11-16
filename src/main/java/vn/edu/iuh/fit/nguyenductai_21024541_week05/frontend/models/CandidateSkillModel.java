package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.CandidateSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;

import java.net.URI;
import java.util.List;

@Component
public class CandidateSkillModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUri = "http://localhost:8080/api/candidate-skill/";

    public CandidateSkillModel() {
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.restTemplate = new RestTemplate();
    }

    // Insert a new CandidateSkill
    public CandidateSkill insert(CandidateSkill candidateSkill) {
        Response response = restTemplate.postForObject(URI.create(baseUri), candidateSkill, Response.class);
        return mapper.convertValue(response.getData(), CandidateSkill.class);
    }

    // Insert multiple CandidateSkills
    public List<CandidateSkill> insertAll(List<CandidateSkill> candidateSkills) {
        Response response = restTemplate.postForObject(URI.create(baseUri + "list"), candidateSkills, Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<CandidateSkill>>() {});
    }

    // Update a CandidateSkill
    public CandidateSkill update(CandidateSkillId candidateSkillId, CandidateSkill candidateSkill) {
        restTemplate.put(URI.create(baseUri + candidateSkillId), candidateSkill);
        return getById(candidateSkillId);
    }

    // Delete a CandidateSkill
    public void delete(CandidateSkillId candidateSkillId) {
        restTemplate.delete(URI.create(baseUri + candidateSkillId));
    }

    // Get a CandidateSkill by ID
    public CandidateSkill getById(CandidateSkillId candidateSkillId) {
        Response response = restTemplate.getForObject(URI.create(baseUri + candidateSkillId), Response.class);
        return mapper.convertValue(response.getData(), CandidateSkill.class);
    }

    // Get all CandidateSkills
    public List<CandidateSkill> getAll() {
        Response response = restTemplate.getForObject(URI.create(baseUri), Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<CandidateSkill>>() {});
    }
}