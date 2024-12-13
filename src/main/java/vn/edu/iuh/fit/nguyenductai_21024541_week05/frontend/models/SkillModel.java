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
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillType;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
public class SkillModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api/skills";  // URL của API Skill

    @Autowired
    public SkillModel(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.mapper.registerModule(new JavaTimeModule());  // Để xử lý dữ liệu thời gian
    }

    // Phương thức thêm một Skill
    public Response insert(Skill skill) {
        URI uri = URI.create(baseUrl);
        return restTemplate.postForObject(uri, skill, Response.class);
    }

    // Phương thức thêm nhiều Skill
    public Response insertAll(List<Skill> skills) {
        URI uri = URI.create(baseUrl + "/bulk");
        return restTemplate.postForObject(uri, skills, Response.class);
    }

    // Phương thức cập nhật một Skill
    public Response update(Long id, Skill skill) {
        URI uri = URI.create(baseUrl + "/" + id);
        restTemplate.put(uri, skill);
        return new Response(HttpStatus.OK.value(), "Skill updated successfully", skill);
    }

    // Phương thức xóa một Skill
    public void delete(Long id) {
        URI uri = URI.create(baseUrl + "/" + id);
        restTemplate.delete(uri);
    }

    // Phương thức lấy Skill theo ID
    public Optional<Skill> getById(Long id) {
        URI uri = URI.create(baseUrl + "/" + id);
        return Optional.ofNullable(restTemplate.getForObject(uri, Skill.class));
    }

    // Phương thức lấy tất cả Skill
    public List<Skill> getAll() {
        URI uri = URI.create(baseUrl);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Skill>>() {}).getBody();
    }

    // Tìm kiếm Skill theo tên (không phân biệt hoa thường)
    public List<Skill> getSkillsByName(String skillName) {
        URI uri = URI.create(baseUrl + "/search?skillName=" + skillName);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Skill>>() {}).getBody();
    }

    // Tìm kiếm Skill theo loại
    public List<Skill> getSkillsByType(SkillType skillType) {
        URI uri = URI.create(baseUrl + "/search/type?skillType=" + skillType);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Skill>>() {}).getBody();
    }
}
