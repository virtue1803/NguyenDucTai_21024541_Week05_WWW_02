package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillType;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.SkillService;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;

import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/skills")
public class SkillResources implements IResources<Skill, Long> {

    @Autowired
    private SkillService skillService;

    @Override
    @PostMapping
    public ResponseEntity<Response> insert(@RequestBody Skill skill) {
        log.info("Inserting skill: {}", skill);
        Skill savedSkill = skillService.add(skill);
        Response response = new Response(HttpStatus.CREATED.value(), "Skill created successfully", savedSkill);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PostMapping("/bulk")
    public ResponseEntity<Response> insertAll(@RequestBody List<Skill> skills) {
        log.info("Inserting multiple skills");
        List<Skill> savedSkills = skillService.addMany(skills);
        Response response = new Response(HttpStatus.CREATED.value(), "Skills created successfully", savedSkills);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody Skill skill) {
        try {
            skill.setId(id);
            Skill updatedSkill = skillService.update(skill);
            Response response = new Response(HttpStatus.OK.value(), "Skill updated successfully", updatedSkill);
            return ResponseEntity.ok(response);
        } catch (EntityIdNotFoundException e) {
            log.error("Skill not found with id: {}", id);
            Response response = new Response(HttpStatus.NOT_FOUND.value(), "Skill not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        try {
            skillService.delete(id);
            Response response = new Response(HttpStatus.NO_CONTENT.value(), "Skill deleted successfully", null);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (EntityIdNotFoundException e) {
            log.error("Skill not found with id: {}", id);
            Response response = new Response(HttpStatus.NOT_FOUND.value(), "Skill not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        try {
            Skill skill = skillService.getById(id).orElseThrow(() -> new EntityIdNotFoundException("Skill not found"));
            Response response = new Response(HttpStatus.OK.value(), "Skill retrieved successfully", skill);
            return ResponseEntity.ok(response);
        } catch (EntityIdNotFoundException e) {
            log.error("Skill not found with id: {}", id);
            Response response = new Response(HttpStatus.NOT_FOUND.value(), "Skill not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<Response> getAll() {
        Iterator<Skill> skills = skillService.getAll();
        Response response = new Response(HttpStatus.OK.value(), "Skills retrieved successfully", skills);
        return ResponseEntity.ok(response);
    }

    // Tìm kiếm kỹ năng theo tên (không phân biệt hoa thường)
    @GetMapping("/search")
    public ResponseEntity<Response> findBySkillName(@RequestParam String skillName) {
        List<Skill> skills = skillService.findBySkillName(skillName);
        Response response = new Response(HttpStatus.OK.value(), "Skills found successfully", skills);
        return ResponseEntity.ok(response);
    }

    // Tìm kiếm kỹ năng theo loại
    @GetMapping("/search/type")
    public ResponseEntity<Response> findBySkillType(@RequestParam SkillType skillType) {
        List<Skill> skills = skillService.findBySkillType(skillType);
        Response response = new Response(HttpStatus.OK.value(), "Skills found by type", skills);
        return ResponseEntity.ok(response);
    }
}
