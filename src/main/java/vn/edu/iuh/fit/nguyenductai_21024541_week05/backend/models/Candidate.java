package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address", nullable = false)
    private Address address;

    public Candidate() {
    }

    public Candidate(LocalDate dob, String email, String fullName, String phone, Address address) {
        this.dob = dob;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }
}