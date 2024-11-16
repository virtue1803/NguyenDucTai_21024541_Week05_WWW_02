package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;

import java.net.URI;
import java.util.List;

@Component
public class SkillModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUri = "http://localhost:8080/api/skill/";

    public SkillModel() {
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.restTemplate = new RestTemplate();
    }

    // Insert a new Skill
    public Skill insert(Skill skill) {
        Response response = restTemplate.postForObject(URI.create(baseUri), skill, Response.class);
        return mapper.convertValue(response.getData(), Skill.class);
    }

    // Insert multiple Skills (Not yet implemented in backend)
    public List<Skill> insertAll(List<Skill> skills) {
        throw new UnsupportedOperationException("Not yet implemented in backend");
    }

    // Update a Skill
    public Skill update(Long id, Skill skill) {
        restTemplate.put(URI.create(baseUri + id), skill);
        return getById(id);
    }

    // Delete a Skill
    public void delete(Long id) {
        restTemplate.delete(URI.create(baseUri + id));
    }

    // Get a Skill by ID
    public Skill getById(Long id) {
        Response response = restTemplate.getForObject(URI.create(baseUri + id), Response.class);
        return mapper.convertValue(response.getData(), Skill.class);
    }

    // Get all Skills
    public List<Skill> getAll() {
        Response response = restTemplate.getForObject(URI.create(baseUri), Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<Skill>>() {});
    }
}
