package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto.CandidateDTO;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.CandidateRole;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.CandidateService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    // Thêm một ứng viên mới
    @PostMapping
    public ResponseEntity<Candidate> addCandidate(@RequestBody Candidate candidate) {
        Candidate createdCandidate = candidateService.add(candidate);
        return new ResponseEntity<>(createdCandidate, HttpStatus.CREATED);
    }

    // Thêm nhiều ứng viên
    @PostMapping("/bulk")
    public ResponseEntity<List<Candidate>> addManyCandidates(@RequestBody List<Candidate> candidates) {
        List<Candidate> createdCandidates = candidateService.addMany(candidates);
        return new ResponseEntity<>(createdCandidates, HttpStatus.CREATED);
    }

    // Cập nhật thông tin ứng viên
    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id, @RequestBody Candidate candidate) {
        candidate.setId(id);
        try {
            Candidate updatedCandidate = candidateService.update(candidate);
            return new ResponseEntity<>(updatedCandidate, HttpStatus.OK);
        } catch (EntityIdNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Xóa ứng viên theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        try {
            candidateService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityIdNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Lấy thông tin ứng viên theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        try {
            Optional<Candidate> candidate = candidateService.getById(id);
            return candidate.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (EntityIdNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        Iterator<Candidate> candidates = candidateService.getAll();
        List<Candidate> candidatesList = new ArrayList<>();
        candidates.forEachRemaining(candidatesList::add);
        return new ResponseEntity<>(candidatesList, HttpStatus.OK);
    }


    // Tìm ứng viên theo email và password
    @PostMapping("/login")
    public ResponseEntity<Candidate> login(@RequestBody CandidateDTO candidateDTO) {
        Optional<Candidate> candidate = candidateService.findByEmailAndPassword(candidateDTO.getEmail(), candidateDTO.getPassword());
        return candidate.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Tìm ứng viên theo vai trò
    @GetMapping("/role/{role}")
    public ResponseEntity<List<Candidate>> findByRole(@PathVariable String role) {
        try {
            CandidateRole candidateRole = CandidateRole.valueOf(role.toUpperCase());
            List<Candidate> candidates = candidateService.findByRole(candidateRole);
            return new ResponseEntity<>(candidates, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Tìm ứng viên theo tên (case-insensitive)
    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<Candidate>> findByName(@PathVariable String name) {
        List<Candidate> candidates = candidateService.findByNameContainingIgnoreCase(name);
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    // Tìm ứng viên theo ngày sinh trong khoảng thời gian
    @GetMapping("/dob")
    public ResponseEntity<List<Candidate>> findByDobBetween(@RequestParam String startDate, @RequestParam String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            List<Candidate> candidates = candidateService.findByDobBetween(start, end);
            return new ResponseEntity<>(candidates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
