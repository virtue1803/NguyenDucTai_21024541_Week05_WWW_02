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
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Company;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
public class CompanyModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api/companies";  // URL của API Company

    @Autowired
    public CompanyModel(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.mapper.registerModule(new JavaTimeModule());  // Để xử lý dữ liệu thời gian
    }

    // Thêm một Company
    public Response insert(Company company) {
        URI uri = URI.create(baseUrl);
        return restTemplate.postForObject(uri, company, Response.class);
    }

    // Thêm nhiều Company
    public Response insertAll(List<Company> companies) {
        URI uri = URI.create(baseUrl + "/bulk");
        return restTemplate.postForObject(uri, companies, Response.class);
    }

    // Cập nhật một Company
    public Response update(Long id, Company company) {
        URI uri = URI.create(baseUrl + "/" + id);
        restTemplate.put(uri, company);
        return new Response(HttpStatus.OK.value(), "Company updated successfully", company);
    }

    // Xóa một Company
    public void delete(Long id) {
        URI uri = URI.create(baseUrl + "/" + id);
        restTemplate.delete(uri);
    }

    // Lấy Company theo ID
    public Optional<Company> getById(Long id) {
        URI uri = URI.create(baseUrl + "/" + id);
        return Optional.ofNullable(restTemplate.getForObject(uri, Company.class));
    }

    // Lấy tất cả Company
    public List<Company> getAll() {
        URI uri = URI.create(baseUrl);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Company>>() {}).getBody();
    }

    // Tìm kiếm Company theo tên
    public List<Company> findByCompName(String name) {
        URI uri = URI.create(baseUrl + "/search/name?name=" + name);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Company>>() {}).getBody();
    }

    // Tìm kiếm Company theo email
    public Optional<Company> findByEmail(String email) {
        URI uri = URI.create(baseUrl + "/search/email?email=" + email);
        return Optional.ofNullable(restTemplate.getForObject(uri, Company.class));
    }
}
