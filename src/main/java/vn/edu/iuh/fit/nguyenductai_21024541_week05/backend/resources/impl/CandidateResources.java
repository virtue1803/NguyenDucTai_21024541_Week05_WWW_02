package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto.CandidateDTO;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.CandidateRole;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.CandidateService;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/candidates")
public class CandidateResources implements IResources<CandidateDTO, Long> {

    @Autowired
    private CandidateService candidateService;

    // Utility method to convert Candidate to CandidateDTO
    private CandidateDTO convertToDTO(Candidate candidate) {
        return new CandidateDTO(
                candidate.getId(),
                candidate.getDob(),
                candidate.getEmail(),
                candidate.getFullName(),
                candidate.getPhone(),
                candidate.getPassword(),
                candidate.getRole(),
                candidate.isStatus(),
                candidate.getAddress()
        );
    }

    // Utility method to convert CandidateDTO to Candidate
    private Candidate convertToEntity(CandidateDTO candidateDTO) {
        Candidate candidate = new Candidate();
        candidate.setId(candidateDTO.getId());
        candidate.setDob(candidateDTO.getDob());
        candidate.setEmail(candidateDTO.getEmail());
        candidate.setFullName(candidateDTO.getFullName());
        candidate.setPhone(candidateDTO.getPhone());
        candidate.setPassword(candidateDTO.getPassword());
        candidate.setRole(candidateDTO.getRole());
        candidate.setStatus(candidateDTO.isStatus());
        return candidate;
    }

    // Thêm ứng viên mới
    @Override
    @PostMapping
    public ResponseEntity<Response> insert(@RequestBody CandidateDTO candidateDTO) {
        try {
            Candidate candidate = convertToEntity(candidateDTO);
            Candidate savedCandidate = candidateService.add(candidate);
            CandidateDTO savedCandidateDTO = convertToDTO(savedCandidate);
            Response response = new Response(HttpStatus.CREATED.value(), "Candidate created successfully", savedCandidateDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), "Failed to create candidate", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Thêm nhiều ứng viên
    @Override
    @PostMapping("/bulk")
    public ResponseEntity<Response> insertAll(@RequestBody List<CandidateDTO> candidateDTOs) {
        try {
            List<Candidate> candidates = candidateDTOs.stream()
                    .map(this::convertToEntity)
                    .collect(Collectors.toList());

            List<Candidate> savedCandidates = candidateService.addMany(candidates);
            List<CandidateDTO> savedCandidateDTOs = savedCandidates.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            Response response = new Response(HttpStatus.CREATED.value(), "Candidates created successfully", savedCandidateDTOs);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), "Failed to create candidates", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Cập nhật ứng viên
//    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody CandidateDTO candidateDTO) {
        try {
            candidateDTO.setId(id);
            Candidate candidate = convertToEntity(candidateDTO);
            Candidate updatedCandidate = candidateService.update(candidate);
            CandidateDTO updatedCandidateDTO = convertToDTO(updatedCandidate);
            Response response = new Response(HttpStatus.OK.value(), "Candidate updated successfully", updatedCandidateDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response response = new Response(HttpStatus.NOT_FOUND.value(), "Candidate not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Xóa ứng viên
//    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        try {
            candidateService.delete(id);
            Response response = new Response(HttpStatus.NO_CONTENT.value(), "Candidate deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            Response response = new Response(HttpStatus.NOT_FOUND.value(), "Candidate not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Lấy thông tin ứng viên theo ID
//    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        try {
            Optional<Candidate> candidate = candidateService.getById(id);
            if (candidate.isPresent()) {
                CandidateDTO candidateDTO = convertToDTO(candidate.get());
                Response response = new Response(HttpStatus.OK.value(), "Candidate found", candidateDTO);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                Response response = new Response(HttpStatus.NOT_FOUND.value(), "Candidate not found", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Response response = new Response(HttpStatus.NOT_FOUND.value(), "Error fetching candidate", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Lấy tất cả ứng viên
    @Override
    @GetMapping
    public ResponseEntity<Response> getAll() {
        try {
            Iterator<Candidate> iterator = candidateService.getAll();
            List<Candidate> candidates = new ArrayList<>();
            iterator.forEachRemaining(candidates::add);

            List<CandidateDTO> candidateDTOs = candidates.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            Response response = new Response(HttpStatus.OK.value(), "All candidates retrieved successfully", candidateDTOs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), "Failed to retrieve candidates", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Tìm kiếm ứng viên theo email và password
    @PostMapping("/login")
    public ResponseEntity<Response> findByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        try {
            Optional<Candidate> candidate = candidateService.findByEmailAndPassword(email, password);
            if (candidate.isPresent()) {
                CandidateDTO candidateDTO = convertToDTO(candidate.get());
                Response response = new Response(HttpStatus.OK.value(), "Candidate found", candidateDTO);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                Response response = new Response(HttpStatus.NOT_FOUND.value(), "Invalid email or password", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error during login", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Tìm kiếm ứng viên theo vai trò
    @GetMapping("/role/{role}")
    public ResponseEntity<Response> findByRole(@PathVariable CandidateRole role) {
        try {
            List<Candidate> candidates = candidateService.findByRole(role);
            List<CandidateDTO> candidateDTOs = candidates.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            Response response = new Response(HttpStatus.OK.value(), "Candidates found", candidateDTOs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), "Error searching by role", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Tìm kiếm ứng viên theo tên
    @GetMapping("/name/{name}")
    public ResponseEntity<Response> findByNameContainingIgnoreCase(@PathVariable String name) {
        try {
            List<Candidate> candidates = candidateService.findByNameContainingIgnoreCase(name);
            List<CandidateDTO> candidateDTOs = candidates.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            Response response = new Response(HttpStatus.OK.value(), "Candidates found", candidateDTOs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), "Error searching by name", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Tìm kiếm ứng viên theo ngày sinh
    @GetMapping("/dob")
    public ResponseEntity<Response> findByDobBetween(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        try {
            List<Candidate> candidates = candidateService.findByDobBetween(startDate, endDate);
            List<CandidateDTO> candidateDTOs = candidates.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            Response response = new Response(HttpStatus.OK.value(), "Candidates found", candidateDTOs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), "Error searching by birthdate", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
