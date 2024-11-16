package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Experience;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;

import java.net.URI;
import java.util.List;

@Component
public class ExperienceModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUri = "http://localhost:8080/api/experience/";

    public ExperienceModel() {
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.restTemplate = new RestTemplate();
    }

    // Insert a new Experience
    public Experience insert(Experience experience) {
        Response response = restTemplate.postForObject(URI.create(baseUri), experience, Response.class);
        return mapper.convertValue(response.getData(), Experience.class);
    }

    // Insert multiple Experiences
    public List<Experience> insertAll(List<Experience> experiences) {
        Response response = restTemplate.postForObject(URI.create(baseUri + "list"), experiences, Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<Experience>>() {});
    }

    // Update an Experience
    public Experience update(Long id, Experience experience) {
        restTemplate.put(URI.create(baseUri + id), experience);
        return getById(id);
    }

    // Delete an Experience
    public void delete(Long id) {
        restTemplate.delete(URI.create(baseUri + id));
    }

    // Get an Experience by ID
    public Experience getById(Long id) {
        Response response = restTemplate.getForObject(URI.create(baseUri + id), Response.class);
        return mapper.convertValue(response.getData(), Experience.class);
    }

    // Get all Experiences
    public List<Experience> getAll() {
        Response response = restTemplate.getForObject(URI.create(baseUri), Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<Experience>>() {});
    }
}