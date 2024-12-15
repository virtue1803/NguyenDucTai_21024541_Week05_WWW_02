package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Address;

import java.io.Serializable;

/**
 * DTO for {@link vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Company}
 */
@Value
@Data
@AllArgsConstructor
public class CompanyDTO implements Serializable {
    Long id;
    String about;
    String email;
    String compName;
    String phone;
    String webUrl;
    String street;
    String city;
    Address address;
}