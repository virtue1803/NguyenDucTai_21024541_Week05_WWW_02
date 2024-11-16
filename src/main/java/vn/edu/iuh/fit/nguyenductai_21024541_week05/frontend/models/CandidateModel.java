package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto.CandidateAccountDTO;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;

import java.net.URI;
import java.util.List;

@Component
public class CandidateModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUri = "http://localhost:8080/api/candidate/";

    public CandidateModel() {
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.restTemplate = new RestTemplate();
    }

    // Insert a new candidate
    public Candidate insert(Candidate candidate) {
        Response response = restTemplate.postForObject(URI.create(baseUri), candidate, Response.class);
        return mapper.convertValue(response.getData(), Candidate.class);
    }

    // Get candidate by ID
    public Candidate getById(Long id) {
        Response response = restTemplate.getForObject(URI.create(baseUri + id), Response.class);
        return mapper.convertValue(response.getData(), Candidate.class);
    }

    // Get all candidates
    public List<Candidate> getAll() {
        Response response = restTemplate.getForObject(URI.create(baseUri), Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<Candidate>>() {});
    }

    // Update candidate
    public Candidate update(Long id, Candidate candidate) {
        restTemplate.put(URI.create(baseUri + id), candidate);
        return getById(id);
    }

    // Delete candidate
    public void delete(Long id) {
        restTemplate.delete(URI.create(baseUri + id));
    }

    // Check login account
    public Candidate checkLoginAccount(CandidateAccountDTO accountDTO) {
        Response response = restTemplate.postForObject(URI.create(baseUri + "login"), accountDTO, Response.class);
        return mapper.convertValue(response.getData(), Candidate.class);
    }

    // Get paginated candidates
    public List<Candidate> getAllPaginated(int page) {
        Response response = restTemplate.getForObject(URI.create(baseUri + "page/" + page), Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<Candidate>>() {});
    }

    // Get candidate's skills
    public List<?> getCandidateSkills(Long candidateId) {
        Response response = restTemplate.getForObject(URI.create(baseUri + candidateId + "/skills"), Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<?>>() {});
    }

    // Get candidate's experiences
    public List<?> getCandidateExperiences(Long candidateId) {
        Response response = restTemplate.getForObject(URI.create(baseUri + candidateId + "/experiences"), Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<?>>() {});


    }

    public List<?> getCandidateSkill(Long candidateId) {
        Response response = restTemplate.getForObject(URI.create(baseUri + candidateId + "/skills"), Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<?>>() {});
    }

}
