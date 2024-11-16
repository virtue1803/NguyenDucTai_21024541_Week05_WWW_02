package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Address;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;

import java.net.URI;
import java.util.List;

@Component
public class AddressModel {
    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final String baseUri = "http://localhost:8080/api/address/";

    public AddressModel() {
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.restTemplate = new RestTemplate();
    }

    // Method to insert a new Address
    public Address insert(Address address) {
        Response response = restTemplate.postForObject(URI.create(baseUri), address, Response.class);
        return mapper.convertValue(response.getData(), Address.class);
    }

    // Method to get Address by ID
    public Address getById(Long id) {
        Response response = restTemplate.getForObject(URI.create(baseUri + id), Response.class);
        return mapper.convertValue(response.getData(), Address.class);
    }

    // Method to get all Addresses
    public List<Address> getAll() {
        Response response = restTemplate.getForObject(URI.create(baseUri), Response.class);
        return mapper.convertValue(response.getData(), new TypeReference<List<Address>>() {});
    }

    // Method to update an Address
    public Address update(Long id, Address address) {
        restTemplate.put(URI.create(baseUri + id), address);
        return getById(id); // Return updated Address
    }

    // Method to delete an Address
    public void delete(Long id) {
        restTemplate.delete(URI.create(baseUri + id));
    }
}
