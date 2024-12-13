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
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Experience;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
public class ExperienceModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api/experiences";  // URL của API Experience

    @Autowired
    public ExperienceModel(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.mapper.registerModule(new JavaTimeModule());  // Để xử lý dữ liệu thời gian
    }

    // Thêm một Experience
    public Response insert(Experience experience) {
        URI uri = URI.create(baseUrl);
        return restTemplate.postForObject(uri, experience, Response.class);
    }

    // Thêm nhiều Experience
    public Response insertAll(List<Experience> experiences) {
        URI uri = URI.create(baseUrl + "/bulk");
        return restTemplate.postForObject(uri, experiences, Response.class);
    }

    // Cập nhật một Experience
    public Response update(Long id, Experience experience) {
        URI uri = URI.create(baseUrl + "/" + id);
        restTemplate.put(uri, experience);
        return new Response(HttpStatus.OK.value(), "Experience updated successfully", experience);
    }

    // Xóa một Experience
    public void delete(Long id) {
        URI uri = URI.create(baseUrl + "/" + id);
        restTemplate.delete(uri);
    }

    // Lấy một Experience theo ID
    public Optional<Experience> getById(Long id) {
        URI uri = URI.create(baseUrl + "/" + id);
        return Optional.ofNullable(restTemplate.getForObject(uri, Experience.class));
    }

    // Lấy tất cả Experience
    public List<Experience> getAll() {
        URI uri = URI.create(baseUrl);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Experience>>() {}).getBody();
    }

    // Tìm kiếm Experience theo ID ứng viên
    public List<Experience> getByCandidateId(Long candidateId) {
        URI uri = URI.create(baseUrl + "/candidate/" + candidateId);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Experience>>() {}).getBody();
    }

    // Tìm kiếm Experience theo tên công ty
    public List<Experience> getByCompanyName(String companyName) {
        URI uri = URI.create(baseUrl + "/company/" + companyName);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Experience>>() {}).getBody();
    }

    // Tìm kiếm Experience theo vai trò
    public List<Experience> getByRole(String role) {
        URI uri = URI.create(baseUrl + "/role/" + role);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Experience>>() {}).getBody();
    }

    // Tìm kiếm Experience theo công ty và vai trò
    public List<Experience> getByCompanyAndRole(String companyName, String role) {
        URI uri = URI.create(baseUrl + "/company/" + companyName + "/role/" + role);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Experience>>() {}).getBody();
    }
}
