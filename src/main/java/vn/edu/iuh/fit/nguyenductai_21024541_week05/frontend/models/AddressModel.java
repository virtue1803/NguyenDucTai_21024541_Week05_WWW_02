package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Address;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
public class AddressModel {

    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api/addresses";  // URL của API Address

    @Autowired
    public AddressModel(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.mapper.registerModule(new JavaTimeModule());  // Để xử lý dữ liệu thời gian
    }

    // Phương thức thêm một Address
    public Response insert(Address address) {
        URI uri = URI.create(baseUrl + "/insert");
        return restTemplate.postForObject(uri, address, Response.class);
    }

    // Phương thức thêm nhiều Address
    public Response insertAll(List<Address> addresses) {
        URI uri = URI.create(baseUrl + "/insertAll");
        return restTemplate.postForObject(uri, addresses, Response.class);
    }

    // Phương thức cập nhật một Address
    public Response update(Long id, Address address) {
        URI uri = URI.create(baseUrl + "/update/" + id);
        restTemplate.put(uri, address);
        return new Response(200, "Update Successful", null);  // Cần trả về Response từ backend thực tế
    }

    // Phương thức xóa một Address
    public Response delete(Long id) {
        URI uri = URI.create(baseUrl + "/delete/" + id);
        restTemplate.delete(uri);
        return new Response(200, "Delete Successful", null);  // Cần trả về Response từ backend thực tế
    }

    // Phương thức lấy Address theo ID
    public Optional<Address> getById(Long id) {
        URI uri = URI.create(baseUrl + "/getById/" + id);
        try {
            Address address = restTemplate.getForObject(uri, Address.class);
            return Optional.ofNullable(address);
        } catch (Exception e) {
            return Optional.empty();  // Return empty Optional if retrieval fails
        }
    }

    // Phương thức lấy tất cả các Address
    public List<Address> getAll() {
        URI uri = URI.create(baseUrl + "/getAll");
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Address>>() {}).getBody();
    }

    // Tìm kiếm Address theo thành phố
    public List<Address> findByCity(String city) {
        URI uri = URI.create(baseUrl + "/findByCity?city=" + city);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Address>>() {}).getBody();
    }

    // Tìm kiếm Address theo quốc gia
    public List<Address> findByCountry(String country) {
        URI uri = URI.create(baseUrl + "/findByCountry?country=" + country);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Address>>() {}).getBody();
    }

    // Tìm kiếm Address theo đường phố
    public List<Address> findByStreet(String street) {
        URI uri = URI.create(baseUrl + "/findByStreet?street=" + street);
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Address>>() {}).getBody();
    }
}
