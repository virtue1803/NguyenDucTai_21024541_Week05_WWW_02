package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto.AddressDTO;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Address;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.AddressService;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/addresses")
public class AddressResources implements IResources<AddressDTO, Long> {

    @Autowired
    private AddressService addressService;

    // Utility method to convert Address to AddressDTO
    private AddressDTO convertToDTO(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getNumber(),
                address.getZipcode()
        );
    }

    // Thêm một địa chỉ mới
    @Override
    @PostMapping
    public ResponseEntity<Response> insert(@RequestBody AddressDTO addressDTO) {
        try {
            // Convert AddressDTO to Address before saving
            Address address = new Address(
                    addressDTO.getId(),
                    addressDTO.getStreet(),
                    addressDTO.getCity(),
                    addressDTO.getCountry(),
                    addressDTO.getNumber(),
                    addressDTO.getZipcode()
            );
            Address savedAddress = addressService.add(address);
            AddressDTO savedAddressDTO = convertToDTO(savedAddress);
            return ResponseEntity.ok(new Response(200, "Address added successfully", savedAddressDTO));
        } catch (Exception e) {
            log.error("Error adding address: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Error adding address", null));
        }
    }

    // Thêm nhiều địa chỉ
    @Override
    @PostMapping("/bulk")
    public ResponseEntity<Response> insertAll(@RequestBody List<AddressDTO> addressDTOs) {
        try {
            List<Address> addresses = addressDTOs.stream().map(dto -> new Address(
                    dto.getId(),
                    dto.getStreet(),
                    dto.getCity(),
                    dto.getCountry(),
                    dto.getNumber(),
                    dto.getZipcode()
            )).collect(Collectors.toList());

            List<Address> savedAddresses = addressService.addMany(addresses);
            List<AddressDTO> savedAddressesDTO = savedAddresses.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new Response(200, "Addresses added successfully", savedAddressesDTO));
        } catch (Exception e) {
            log.error("Error adding addresses: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Error adding addresses", null));
        }
    }

    // Cập nhật một địa chỉ
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        try {
            // Convert AddressDTO to Address before updating
            Address address = new Address(
                    id,
                    addressDTO.getStreet(),
                    addressDTO.getCity(),
                    addressDTO.getCountry(),
                    addressDTO.getNumber(),
                    addressDTO.getZipcode()
            );
            Address updatedAddress = addressService.update(address);
            AddressDTO updatedAddressDTO = convertToDTO(updatedAddress);
            return ResponseEntity.ok(new Response(200, "Address updated successfully", updatedAddressDTO));
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
            AddressDTO addressDTO = convertToDTO(address);
            return ResponseEntity.ok(new Response(200, "Address found", addressDTO));
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
            // Convert Iterator to List
            List<Address> addressList = new ArrayList<>();
            addresses.forEachRemaining(addressList::add);

            // Convert List<Address> to List<AddressDTO>
            List<AddressDTO> addressDTOs = addressList.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new Response(200, "Addresses retrieved successfully", addressDTOs));
        } catch (Exception e) {
            log.error("Error fetching addresses: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Error fetching addresses", null));
        }
    }


    // Tìm kiếm địa chỉ theo thành phố
    @GetMapping("/city/{city}")
    public ResponseEntity<Response> getByCity(@PathVariable String city) {
        List<Address> addresses = addressService.findByCity(city);
        List<AddressDTO> addressDTOs = addresses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new Response(200, "Addresses found by city", addressDTOs));
    }

    // Tìm kiếm địa chỉ theo quốc gia
    @GetMapping("/country/{country}")
    public ResponseEntity<Response> getByCountry(@PathVariable String country) {
        List<Address> addresses = addressService.findByCountry(country);
        List<AddressDTO> addressDTOs = addresses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new Response(200, "Addresses found by country", addressDTOs));
    }

    // Tìm kiếm địa chỉ theo đường phố
    @GetMapping("/street/{street}")
    public ResponseEntity<Response> getByStreet(@PathVariable String street) {
        List<Address> addresses = addressService.findByStreet(street);
        List<AddressDTO> addressDTOs = addresses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new Response(200, "Addresses found by street", addressDTOs));
    }
}
