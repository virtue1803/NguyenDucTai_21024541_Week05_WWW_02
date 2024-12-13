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
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.CandidateRole;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class CandidateModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api/candidates";  // URL của API Candidate

    @Autowired
    public CandidateModel(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.mapper.registerModule(new JavaTimeModule());  // Để xử lý dữ liệu thời gian
    }

    // Phương thức thêm một Candidate
    public Response insert(Candidate candidate) {
        URI uri = URI.create(baseUrl);
        return restTemplate.postForObject(uri, candidate, Response.class);
    }

    // Phương thức thêm nhiều Candidate
    public Response insertAll(List<Candidate> candidates) {
        URI uri = URI.create(baseUrl + "/bulk");
        return restTemplate.postForObject(uri, candidates, Response.class);
    }

    // Phương thức cập nhật một Candidate
    public Response update(Long id, Candidate candidate) {
        URI uri = URI.create(baseUrl + "/" + id);
        restTemplate.put(uri, candidate);
        return new Response(HttpStatus.OK.value(), "Candidate updated successfully", candidate);
    }

    // Phương thức xóa một Candidate
    public void delete(Long id) {
        URI uri = URI.create(baseUrl + "/" + id);
        restTemplate.delete(uri);
    }

    // Phương thức lấy Candidate theo ID
    public Optional<Candidate> getById(Long id) {
        URI uri = URI.create(baseUrl + "/" + id);
        return Optional.ofNullable(restTemplate.getForObject(uri, Candidate.class));
    }

    // Phương thức lấy tất cả Candidate
    public List<Candidate> getAll() {
        URI uri = URI.create(baseUrl);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Candidate>>() {}).getBody();
    }

    // Phương thức tìm kiếm Candidate theo email và password
    public Optional<Candidate> findByEmailAndPassword(String email, String password) {
        URI uri = URI.create(baseUrl + "/login?email=" + email + "&password=" + password);
        return Optional.ofNullable(restTemplate.getForObject(uri, Candidate.class));
    }

    // Phương thức tìm kiếm Candidate theo vai trò
    public List<Candidate> findByRole(CandidateRole role) {
        URI uri = URI.create(baseUrl + "/role/" + role);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Candidate>>() {}).getBody();
    }

    // Phương thức tìm kiếm Candidate theo tên
    public List<Candidate> findByNameContainingIgnoreCase(String name) {
        URI uri = URI.create(baseUrl + "/name/" + name);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Candidate>>() {}).getBody();
    }

    // Phương thức tìm kiếm Candidate theo ngày sinh
    public List<Candidate> findByDobBetween(LocalDate startDate, LocalDate endDate) {
        URI uri = URI.create(baseUrl + "/dob?startDate=" + startDate + "&endDate=" + endDate);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Candidate>>() {}).getBody();
    }
}
