package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Address;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.AddressService;

import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/addresses")
public class AddressResources implements IResources<Address, Long> {

    @Autowired
    private AddressService addressService;

    // Thêm một địa chỉ mới
    @Override
    @PostMapping
    public ResponseEntity<Response> insert(@RequestBody Address address) {
        try {
            Address savedAddress = addressService.add(address);
            return ResponseEntity.ok(new Response(200, "Address added successfully", savedAddress));
        } catch (Exception e) {
            log.error("Error adding address: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Error adding address", null));
        }
    }

    // Thêm nhiều địa chỉ
    @Override
    @PostMapping("/bulk")
    public ResponseEntity<Response> insertAll(@RequestBody List<Address> addresses) {
        try {
            List<Address> savedAddresses = addressService.addMany(addresses);
            return ResponseEntity.ok(new Response(200, "Addresses added successfully", savedAddresses));
        } catch (Exception e) {
            log.error("Error adding addresses: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Error adding addresses", null));
        }
    }

    // Cập nhật một địa chỉ
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody Address address) {
        try {
            address.setId(id);
            Address updatedAddress = addressService.update(address);
            return ResponseEntity.ok(new Response(200, "Address updated successfully", updatedAddress));
        } catch (Exception e) {
            log.error("Error updating address: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Error updating address", null));
        }
    }

    // Xóa địa chỉ theo ID
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        try {
            addressService.delete(id);
            return ResponseEntity.ok(new Response(200, "Address deleted successfully", null));
        } catch (Exception e) {
            log.error("Error deleting address: {}", e.getMessage());
            return ResponseEntity.status(404).body(new Response(404, "Address not found", null));
        }
    }

    // Lấy thông tin địa chỉ theo ID
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        try {
            Address address = addressService.getById(id).orElse(null);
            if (address == null) {
                return ResponseEntity.status(404).body(new Response(404, "Address not found", null));
            }
            return ResponseEntity.ok(new Response(200, "Address found", address));
        } catch (Exception e) {
            log.error("Error fetching address: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Error fetching address", null));
        }
    }

    // Lấy tất cả địa chỉ
    @Override
    @GetMapping
    public ResponseEntity<Response> getAll() {
        try {
            Iterator<Address> addresses = addressService.getAll();
            return ResponseEntity.ok(new Response(200, "Addresses retrieved successfully", addresses));
        } catch (Exception e) {
            log.error("Error fetching addresses: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Error fetching addresses", null));
        }
    }

    // Tìm kiếm địa chỉ theo thành phố
    @GetMapping("/city/{city}")
    public ResponseEntity<Response> getByCity(@PathVariable String city) {
        List<Address> addresses = addressService.findByCity(city);
        return ResponseEntity.ok(new Response(200, "Addresses found by city", addresses));
    }

    // Tìm kiếm địa chỉ theo quốc gia
    @GetMapping("/country/{country}")
    public ResponseEntity<Response> getByCountry(@PathVariable String country) {
        List<Address> addresses = addressService.findByCountry(country);
        return ResponseEntity.ok(new Response(200, "Addresses found by country", addresses));
    }

    // Tìm kiếm địa chỉ theo đường phố
    @GetMapping("/street/{street}")
    public ResponseEntity<Response> getByStreet(@PathVariable String street) {
        List<Address> addresses = addressService.findByStreet(street);
        return ResponseEntity.ok(new Response(200, "Addresses found by street", addresses));
    }
}
