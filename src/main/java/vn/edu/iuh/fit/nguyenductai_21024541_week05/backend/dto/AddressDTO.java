package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Address}
 */
@Value
@AllArgsConstructor
@Data
public class AddressDTO implements Serializable {
    Long id;
    String street;
    String city;
    String country;
    String number;
    String zipcode;
}