package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.CandidateRole;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.CandidateService;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/candidates")
public class CandidateResources implements IResources<Candidate, Long> {

    @Autowired
    private CandidateService candidateService;

    // Thêm ứng viên mới
    @Override
    @PostMapping
    public ResponseEntity<Response> insert(@RequestBody Candidate candidate) {
        try {
            Candidate savedCandidate = candidateService.add(candidate);
            Response response = new Response(HttpStatus.CREATED.value(), "Candidate created successfully", savedCandidate);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), "Failed to create candidate", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Thêm nhiều ứng viên
    @Override
    @PostMapping("/bulk")
    public ResponseEntity<Response> insertAll(@RequestBody List<Candidate> candidates) {
        try {
            List<Candidate> savedCandidates = candidateService.addMany(candidates);
            Response response = new Response(HttpStatus.CREATED.value(), "Candidates created successfully", savedCandidates);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), "Failed to create candidates", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Cập nhật ứng viên
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody Candidate candidate) {
        try {
            candidate.setId(id);
            Candidate updatedCandidate = candidateService.update(candidate);
            Response response = new Response(HttpStatus.OK.value(), "Candidate updated successfully", updatedCandidate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response response = new Response(HttpStatus.NOT_FOUND.value(), "Candidate not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Xóa ứng viên
    @Override
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
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        try {
            Optional<Candidate> candidate = candidateService.getById(id);
            if (candidate.isPresent()) {
                Response response = new Response(HttpStatus.OK.value(), "Candidate found", candidate.get());
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
            List<Candidate> candidates = (List<Candidate>) candidateService.getAll();
            Response response = new Response(HttpStatus.OK.value(), "All candidates retrieved successfully", candidates);
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
                Response response = new Response(HttpStatus.OK.value(), "Candidate found", candidate.get());
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
            Response response = new Response(HttpStatus.OK.value(), "Candidates found", candidates);
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
            Response response = new Response(HttpStatus.OK.value(), "Candidates found", candidates);
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
            Response response = new Response(HttpStatus.OK.value(), "Candidates found", candidates);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), "Error searching by birthdate", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
