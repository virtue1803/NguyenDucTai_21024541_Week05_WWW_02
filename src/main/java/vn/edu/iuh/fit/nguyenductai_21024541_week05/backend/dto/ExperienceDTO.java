package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Experience}
 */
@Value
@Data
@AllArgsConstructor
public class ExperienceDTO implements Serializable {
    long expId;
    String companyName;
    String workDescription;
    String role;
    LocalDate fromDate;
    LocalDate toDate;
}