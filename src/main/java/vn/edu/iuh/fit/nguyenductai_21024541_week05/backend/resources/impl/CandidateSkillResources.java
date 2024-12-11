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

import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/candidate-skills")
public class CandidateSkillResources implements IResources<CandidateSkill, CandidateSkillId> {

    @Autowired
    private CandidateSkillService candidateSkillService;

    @Override
    @PostMapping
    public ResponseEntity<Response> insert(@RequestBody CandidateSkill candidateSkill) {
        CandidateSkill savedSkill = candidateSkillService.add(candidateSkill);
        Response response = new Response(200, "Skill added successfully", savedSkill);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/bulk")
    public ResponseEntity<Response> insertAll(@RequestBody List<CandidateSkill> candidateSkills) {
        List<CandidateSkill> savedSkills = candidateSkillService.addMany(candidateSkills);
        Response response = new Response(200, "Skills added successfully", savedSkills);
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable("id") CandidateSkillId id, @RequestBody CandidateSkill candidateSkill) {
        try {
            candidateSkill.setId(id);  // Ensure the ID is set correctly before updating
            CandidateSkill updatedSkill = candidateSkillService.update(candidateSkill);
            Response response = new Response(200, "Skill updated successfully", updatedSkill);
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
            Response response = new Response(200, "Skill retrieved successfully", candidateSkill);
            return ResponseEntity.ok(response);
        } catch (EntityIdNotFoundException e) {
            Response response = new Response(404, "Skill not found", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<Response> getAll() {
        Iterator<CandidateSkill> allSkills = candidateSkillService.getAll();
        Response response = new Response(200, "All skills retrieved successfully", allSkills);
        return ResponseEntity.ok(response);
    }

    // Các phương thức tìm kiếm riêng cho CandidateSkill

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<Response> getSkillsByCandidateId(@PathVariable Long candidateId) {
        List<CandidateSkill> skills = candidateSkillService.findByCandidateId(candidateId);
        Response response = new Response(200, "Skills retrieved for candidate", skills);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/level/{skillLevel}")
    public ResponseEntity<Response> getSkillsByLevel(@PathVariable SkillLevel skillLevel) {
        List<CandidateSkill> skills = candidateSkillService.findBySkillLevel(skillLevel);
        Response response = new Response(200, "Skills retrieved for level", skills);
        return ResponseEntity.ok(response);
    }
}
