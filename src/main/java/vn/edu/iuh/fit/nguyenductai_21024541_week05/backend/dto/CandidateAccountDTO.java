package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidateAccountDTO {
    private String email;
    private String password;
}
