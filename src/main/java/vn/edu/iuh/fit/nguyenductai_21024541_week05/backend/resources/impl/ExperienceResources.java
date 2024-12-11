package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Experience;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.ExperienceService;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/experiences")
public class ExperienceResources implements IResources<Experience, Long> {

    @Autowired
    private ExperienceService experienceService;

    // Thêm mới một kinh nghiệm
    @Override
    @PostMapping
    public ResponseEntity<Response> insert(@RequestBody Experience experience) {
        try {
            Experience savedExperience = experienceService.add(experience);
            Response response = new Response(200, "Experience created successfully", savedExperience);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response(500, "Failed to create experience", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    // Thêm nhiều kinh nghiệm
    @Override
    @PostMapping("/bulk")
    public ResponseEntity<Response> insertAll(@RequestBody List<Experience> experiences) {
        try {
            List<Experience> savedExperiences = experienceService.addMany(experiences);
            Response response = new Response(200, "Experiences created successfully", savedExperiences);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response(500, "Failed to create experiences", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    // Cập nhật một kinh nghiệm
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody Experience experience) {
        try {
            experience.setExpId(id);  // Ensure the ID is set for updating
            Experience updatedExperience = experienceService.update(experience);
            Response response = new Response(200, "Experience updated successfully", updatedExperience);
            return ResponseEntity.ok(response);
        } catch (EntityIdNotFoundException e) {
            Response response = new Response(404, "Experience not found", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    // Xóa một kinh nghiệm
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        try {
            experienceService.delete(id);
            Response response = new Response(200, "Experience deleted successfully", null);
            return ResponseEntity.ok(response);
        } catch (EntityIdNotFoundException e) {
            Response response = new Response(404, "Experience not found", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    // Lấy một kinh nghiệm theo ID
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        try {
            Optional<Experience> experience = experienceService.getById(id);
            if (experience.isPresent()) {
                Response response = new Response(200, "Experience found", experience.get());
                return ResponseEntity.ok(response);
            } else {
                Response response = new Response(404, "Experience not found", null);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            Response response = new Response(500, "Error retrieving experience", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    // Lấy tất cả các kinh nghiệm
    @Override
    @GetMapping
    public ResponseEntity<Response> getAll() {
        try {
            Iterator<Experience> experiences = experienceService.getAll();
            Response response = new Response(200, "Experiences retrieved successfully", experiences);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response(500, "Error retrieving experiences", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    // Tìm kiếm kinh nghiệm theo ID ứng viên
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<Response> getByCandidateId(@PathVariable Long candidateId) {
        try {
            List<Experience> experiences = experienceService.findByCandidateId(candidateId);
            Response response = new Response(200, "Experiences retrieved successfully", experiences);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response(500, "Error retrieving experiences by candidate", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    // Tìm kiếm kinh nghiệm theo tên công ty
    @GetMapping("/company/{companyName}")
    public ResponseEntity<Response> getByCompanyName(@PathVariable String companyName) {
        try {
            List<Experience> experiences = experienceService.findByCompanyName(companyName);
            Response response = new Response(200, "Experiences retrieved successfully", experiences);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response(500, "Error retrieving experiences by company", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    // Tìm kiếm kinh nghiệm theo vai trò
    @GetMapping("/role/{role}")
    public ResponseEntity<Response> getByRole(@PathVariable String role) {
        try {
            List<Experience> experiences = experienceService.findByRole(role);
            Response response = new Response(200, "Experiences retrieved successfully", experiences);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response(500, "Error retrieving experiences by role", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    // Tìm kiếm kinh nghiệm theo công ty và vai trò
    @GetMapping("/company/{companyName}/role/{role}")
    public ResponseEntity<Response> getByCompanyAndRole(@PathVariable String companyName, @PathVariable String role) {
        try {
            List<Experience> experiences = experienceService.findByCompanyNameAndRole(companyName, role);
            Response response = new Response(200, "Experiences retrieved successfully", experiences);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response(500, "Error retrieving experiences by company and role", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}
