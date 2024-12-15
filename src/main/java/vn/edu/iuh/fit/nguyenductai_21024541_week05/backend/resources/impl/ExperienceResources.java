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
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto.ExperienceDTO;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
            ExperienceDTO experienceDTO = convertToDTO(savedExperience);
            Response response = new Response(200, "Experience created successfully", experienceDTO);
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
            List<ExperienceDTO> experienceDTOs = savedExperiences.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            Response response = new Response(200, "Experiences created successfully", experienceDTOs);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response(500, "Failed to create experiences", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    // Cập nhật một kinh nghiệm
//    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody Experience experience) {
        try {
            experience.setExpId(id);  // Ensure the ID is set for updating
            Experience updatedExperience = experienceService.update(experience);
            ExperienceDTO experienceDTO = convertToDTO(updatedExperience);
            Response response = new Response(200, "Experience updated successfully", experienceDTO);
            return ResponseEntity.ok(response);
        } catch (EntityIdNotFoundException e) {
            Response response = new Response(404, "Experience not found", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    // Xóa một kinh nghiệm
//    @Override
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
//    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        try {
            Optional<Experience> experience = experienceService.getById(id);
            if (experience.isPresent()) {
                ExperienceDTO experienceDTO = convertToDTO(experience.get());
                Response response = new Response(200, "Experience found", experienceDTO);
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
            // Fetch all experiences
            Iterator<Experience> experiencesIterator = experienceService.getAll();

            // Convert the Iterator to a List using StreamSupport
            List<Experience> experiencesList = StreamSupport.stream(((Iterable<Experience>) () -> experiencesIterator).spliterator(), false)
                    .toList();

            // Check if experiencesList is empty
            if (experiencesList.isEmpty()) {
                return ResponseEntity.status(404).body(new Response(404, "No experiences found", null));
            }

            // Convert the List of experiences to a List of ExperienceDTOs
            List<ExperienceDTO> experienceDTOs = experiencesList.stream()
                    .map(experience -> new ExperienceDTO(experience.getExpId(), experience.getCompanyName(), experience.getWorkDescription(), experience.getRole(), experience.getFromDate(), experience.getToDate()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new Response(200, "Experiences retrieved successfully", experienceDTOs));
        } catch (Exception e) {
            log.error("Error retrieving experiences: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Error retrieving experiences", null));
        }
    }


    // Tìm kiếm kinh nghiệm theo ID ứng viên
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<Response> getByCandidateId(@PathVariable Long candidateId) {
        try {
            List<Experience> experiences = experienceService.findByCandidateId(candidateId);
            List<ExperienceDTO> experienceDTOs = experiences.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            Response response = new Response(200, "Experiences retrieved successfully", experienceDTOs);
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
            List<ExperienceDTO> experienceDTOs = experiences.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            Response response = new Response(200, "Experiences retrieved successfully", experienceDTOs);
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
            List<ExperienceDTO> experienceDTOs = experiences.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            Response response = new Response(200, "Experiences retrieved successfully", experienceDTOs);
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
            List<ExperienceDTO> experienceDTOs = experiences.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            Response response = new Response(200, "Experiences retrieved successfully", experienceDTOs);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response(500, "Error retrieving experiences by company and role", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    // Helper methods to convert Experience to ExperienceDTO
    private ExperienceDTO convertToDTO(Experience experience) {
        return new ExperienceDTO(
                experience.getExpId(),
                experience.getCompanyName(),
                experience.getWorkDescription(),
                experience.getRole(),
                experience.getFromDate(),
                experience.getToDate()
        );
    }

    private List<ExperienceDTO> convertToDTOList(Iterator<Experience> experiences) {
        return experiences == null ? List.of() :
                ((List<Experience>) experiences).stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
    }
}
