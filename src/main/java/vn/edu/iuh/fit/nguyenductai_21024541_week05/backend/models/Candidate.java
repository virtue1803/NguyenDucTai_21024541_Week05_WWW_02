package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.CandidateRole;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "candidate")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @NonNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NonNull
    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @NonNull
    @Column(nullable = false, length = 100)
    private String password;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address", nullable = false)
    private Address address;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CandidateRole role;

    @Column(nullable = false)
    private boolean status;

    @PrePersist
    public void prePersist() {
        status = true;
        role = CandidateRole.USER;
    }
}
