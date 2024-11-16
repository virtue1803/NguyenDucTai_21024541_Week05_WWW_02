package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Job;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;

import java.net.URI;
import java.util.List;

@Component
public class JobModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUri = "http://localhost:8080/api/jobs/";

    public JobModel() {
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.restTemplate = new RestTemplate();
    }

    // Insert a new Job
    public Job insert(Job job) {
        Response response = restTemplate.postForObject(URI.create(baseUri), job, Response.class);
        return mapper.convertValue(response.getData(), Job.class);
    }

    // Insert multiple Jobs
    public List<Job> insertAll(List<Job> jobs) {
        Response response = restTemplate.postForObject(URI.create(baseUri + "list"), jobs, Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<Job>>() {});
    }

    // Update a Job
    public Job update(Long id, Job job) {
        restTemplate.put(URI.create(baseUri + id), job);
        return getById(id);
    }

    // Delete a Job
    public void delete(Long id) {
        restTemplate.delete(URI.create(baseUri + id));
    }

    // Get a Job by ID
    public Job getById(Long id) {
        Response response = restTemplate.getForObject(URI.create(baseUri + id), Response.class);
        return mapper.convertValue(response.getData(), Job.class);
    }

    // Get all Jobs
    public List<Job> getAll() {
        Response response = restTemplate.getForObject(URI.create(baseUri), Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<Job>>() {});
    }
}