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
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Job;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
public class JobModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/jobs";  // URL của API Job

    @Autowired
    public JobModel(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.mapper.registerModule(new JavaTimeModule());  // Để xử lý dữ liệu thời gian
    }

    // Thêm một Job
    public Response insert(Job job) {
        URI uri = URI.create(baseUrl + "/insert");
        return restTemplate.postForObject(uri, job, Response.class);
    }

    // Thêm nhiều Job
    public Response insertAll(List<Job> jobs) {
        URI uri = URI.create(baseUrl + "/insertAll");
        return restTemplate.postForObject(uri, jobs, Response.class);
    }

    // Cập nhật một Job
    public Response update(Long id, Job job) {
        URI uri = URI.create(baseUrl + "/update/" + id);
        restTemplate.put(uri, job);
        return new Response(HttpStatus.OK.value(), "Job updated successfully", job);
    }

    // Xóa một Job
    public void delete(Long id) {
        URI uri = URI.create(baseUrl + "/delete/" + id);
        restTemplate.delete(uri);
    }

    // Lấy một Job theo ID
    public Optional<Job> getById(Long id) {
        URI uri = URI.create(baseUrl + "/get/" + id);
        return Optional.ofNullable(restTemplate.getForObject(uri, Job.class));
    }

    // Lấy tất cả Job
    public List<Job> getAll() {
        URI uri = URI.create(baseUrl + "/getAll");
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Job>>() {}).getBody();
    }

    // Tìm kiếm Job theo tên công việc
    public List<Job> findByJobName(String jobName) {
        URI uri = URI.create(baseUrl + "/searchByJobName?jobName=" + jobName);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Job>>() {}).getBody();
    }

    // Tìm kiếm Job theo tên công ty
    public List<Job> findByCompanyName(String companyName) {
        URI uri = URI.create(baseUrl + "/searchByCompanyName?companyName=" + companyName);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Job>>() {}).getBody();
    }

    // Tìm kiếm Job theo mô tả công việc
    public List<Job> findByJobDesc(String jobDesc) {
        URI uri = URI.create(baseUrl + "/searchByJobDesc?jobDesc=" + jobDesc);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Job>>() {}).getBody();
    }
}
