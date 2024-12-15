package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.CandidateRole;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Address;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDTO implements Serializable {
    private Long id;
    private LocalDate dob;
    private String email;
    private String fullName;
    private String phone;
    private String password;
    private CandidateRole role;
    private boolean status;
    Address address;


}