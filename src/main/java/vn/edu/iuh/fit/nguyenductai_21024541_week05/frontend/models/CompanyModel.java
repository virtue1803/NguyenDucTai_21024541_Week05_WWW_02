package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Company;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;

import java.net.URI;
import java.util.List;

@Component
public class CompanyModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUri = "http://localhost:8080/api/company/";

    public CompanyModel() {
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.restTemplate = new RestTemplate();
    }

    // Insert a new company
    public Company insert(Company company) {
        Response response = restTemplate.postForObject(URI.create(baseUri), company, Response.class);
        return mapper.convertValue(response.getData(), Company.class);
    }

    // Insert multiple companies
    public List<Company> insertAll(List<Company> companies) {
        Response response = restTemplate.postForObject(URI.create(baseUri + "list"), companies, Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<Company>>() {});
    }

    // Update a company
    public Company update(Long id, Company company) {
        restTemplate.put(URI.create(baseUri + id), company);
        return getById(id); // Fetch updated company details
    }

    // Delete a company
    public void delete(Long id) {
        restTemplate.delete(URI.create(baseUri + id));
    }

    // Get a company by ID
    public Company getById(Long id) {
        Response response = restTemplate.getForObject(URI.create(baseUri + id), Response.class);
        return mapper.convertValue(response.getData(), Company.class);
    }

    // Get all companies
    public List<Company> getAll() {
        Response response = restTemplate.getForObject(URI.create(baseUri), Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<Company>>() {});
    }
}