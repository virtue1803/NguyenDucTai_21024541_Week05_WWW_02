package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Experience {
    @Id
    @Column(name = "exp_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long expId;
    @Column(name = "company", length = 120, nullable = false)
    private String companyName;
    @Column(name = "work_desc", length = 400)
    private String workDescription;
    @Column(nullable = false, length = 100)
    private String role;
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;
    @ManyToOne @JoinColumn(name = "can_id", nullable = false)
    private Candidate candidate;

    public Experience(String companyName, String workDescription, String role, LocalDate fromDate, LocalDate toDate, Candidate candidate) {
        this.companyName = companyName;
        this.workDescription = workDescription;
        this.role = role;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.candidate = candidate;
    }
}