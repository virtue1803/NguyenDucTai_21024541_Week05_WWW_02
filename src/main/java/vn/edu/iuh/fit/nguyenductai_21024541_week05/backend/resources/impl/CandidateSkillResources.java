package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.CandidateSkillService;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.CandidateSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto.CandidateSkillDTO;

import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@RestController
@RequestMapping("/api/candidate-skills")
public class CandidateSkillResources implements IResources<CandidateSkillDTO, CandidateSkillId> {

    @Autowired
    private CandidateSkillService candidateSkillService;

    // Convert CandidateSkill to CandidateSkillDTO
    private CandidateSkillDTO convertToDTO(CandidateSkill candidateSkill) {
        return new CandidateSkillDTO(candidateSkill.getMoreInfos(), candidateSkill.getSkillLevel());
    }

    // Convert CandidateSkillDTO to CandidateSkill
    private CandidateSkill convertToEntity(CandidateSkillDTO candidateSkillDTO) {
        CandidateSkill candidateSkill = new CandidateSkill();
        candidateSkill.setMoreInfos(candidateSkillDTO.getMoreInfos());
        candidateSkill.setSkillLevel(candidateSkillDTO.getSkillLevel());
        return candidateSkill;
    }

    @Override
    @PostMapping
    public ResponseEntity<Response> insert(@RequestBody CandidateSkillDTO candidateSkillDTO) {
        try {
            CandidateSkill candidateSkill = convertToEntity(candidateSkillDTO);
            CandidateSkill savedSkill = candidateSkillService.add(candidateSkill);
            CandidateSkillDTO responseDTO = convertToDTO(savedSkill);
            Response response = new Response(200, "Skill added successfully", responseDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response(400, "Error adding skill", null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @Override
    @PostMapping("/bulk")
    public ResponseEntity<Response> insertAll(@RequestBody List<CandidateSkillDTO> candidateSkillDTOs) {
        try {
            List<CandidateSkill> candidateSkills = candidateSkillDTOs.stream()
                    .map(this::convertToEntity)
                    .toList();
            List<CandidateSkill> savedSkills = candidateSkillService.addMany(candidateSkills);
            List<CandidateSkillDTO> responseDTOs = savedSkills.stream()
                    .map(this::convertToDTO)
                    .toList();
            Response response = new Response(200, "Skills added successfully", responseDTOs);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response(400, "Error adding skills", null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable("id") CandidateSkillId id, @RequestBody CandidateSkillDTO candidateSkillDTO) {
        try {
            CandidateSkill candidateSkill = convertToEntity(candidateSkillDTO);
            candidateSkill.setId(id);  // Ensure the ID is set correctly before updating
            CandidateSkill updatedSkill = candidateSkillService.update(candidateSkill);
            CandidateSkillDTO responseDTO = convertToDTO(updatedSkill);
            Response response = new Response(200, "Skill updated successfully", responseDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response(404, "Skill not found", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") CandidateSkillId id) {
        try {
            candidateSkillService.delete(id);
            Response response = new Response(200, "Skill deleted successfully", null);
            return ResponseEntity.ok(response);
        } catch (EntityIdNotFoundException e) {
            Response response = new Response(404, "Skill not found", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable("id") CandidateSkillId id) {
        try {
            CandidateSkill candidateSkill = candidateSkillService.getById(id).orElseThrow(() -> new EntityIdNotFoundException("Skill not found"));
            CandidateSkillDTO responseDTO = convertToDTO(candidateSkill);
            Response response = new Response(200, "Skill retrieved successfully", responseDTO);
            return ResponseEntity.ok(response);
        } catch (EntityIdNotFoundException e) {
            Response response = new Response(404, "Skill not found", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<Response> getAll() {
        Iterable<CandidateSkill> allSkills = () -> candidateSkillService.getAll(); // Convert Iterator to Iterable
        List<CandidateSkillDTO> responseDTOs = StreamSupport.stream(allSkills.spliterator(), false) // Use StreamSupport to convert to stream
                .map(this::convertToDTO)
                .toList();  // Collect to list
        Response response = new Response(200, "All skills retrieved successfully", responseDTOs);
        return ResponseEntity.ok(response);
    }



    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<Response> getSkillsByCandidateId(@PathVariable Long candidateId) {
        List<CandidateSkill> skills = candidateSkillService.findByCandidateId(candidateId);
        List<CandidateSkillDTO> responseDTOs = skills.stream()
                .map(this::convertToDTO)
                .toList();
        Response response = new Response(200, "Skills retrieved for candidate", responseDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/level/{skillLevel}")
    public ResponseEntity<Response> getSkillsByLevel(@PathVariable SkillLevel skillLevel) {
        List<CandidateSkill> skills = candidateSkillService.findBySkillLevel(skillLevel);
        List<CandidateSkillDTO> responseDTOs = skills.stream()
                .map(this::convertToDTO)
                .toList();
        Response response = new Response(200, "Skills retrieved for level", responseDTOs);
        return ResponseEntity.ok(response);
    }
}
