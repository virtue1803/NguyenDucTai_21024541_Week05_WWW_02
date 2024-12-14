package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Job}
 */
@Value
@Data
@AllArgsConstructor
public class JobDTO implements Serializable {
    Long id;
    String jobDesc;
    String jobName;
}