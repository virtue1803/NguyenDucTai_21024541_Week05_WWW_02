package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.JobSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.JobSkillService;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.JobSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/job-skills")
public class JobSkillResources implements IResources<JobSkill, JobSkillId> {

    @Autowired
    private JobSkillService jobSkillService;

    // Insert a new JobSkill
    @Override
    @PostMapping
    public ResponseEntity<Response> insert(@RequestBody JobSkill jobSkill) {
        try {
            JobSkill savedJobSkill = jobSkillService.add(jobSkill);
            Response response = new Response(201, "Job Skill added successfully", savedJobSkill);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error inserting JobSkill: ", e);
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Insert multiple JobSkills
    @Override
    @PostMapping("/bulk")
    public ResponseEntity<Response> insertAll(@RequestBody List<JobSkill> jobSkills) {
        try {
            List<JobSkill> savedJobSkills = jobSkillService.addMany(jobSkills);
            Response response = new Response(201, "Job Skills added successfully", savedJobSkills);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error inserting multiple JobSkills: ", e);
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Update JobSkill by ID
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable JobSkillId id, @RequestBody JobSkill jobSkill) {
        try {
            jobSkill.setId(id); // Ensure we update the correct JobSkill
            JobSkill updatedJobSkill = jobSkillService.update(jobSkill);
            Response response = new Response(200, "Job Skill updated successfully", updatedJobSkill);
            return ResponseEntity.ok(response);
        } catch (EntityIdNotFoundException e) {
            log.error("JobSkill not found: ", e);
            Response response = new Response(404, "JobSkill not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            log.error("Error updating JobSkill: ", e);
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Delete JobSkill by ID
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable JobSkillId id) {
        try {
            jobSkillService.delete(id);
            Response response = new Response(200, "Job Skill deleted successfully", null);
            return ResponseEntity.ok(response);
        } catch (EntityIdNotFoundException e) {
            log.error("JobSkill not found for deletion: ", e);
            Response response = new Response(404, "JobSkill not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            log.error("Error deleting JobSkill: ", e);
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Get JobSkill by ID
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable JobSkillId id) {
        try {
            JobSkill jobSkill = jobSkillService.getById(id).orElseThrow(() -> new EntityIdNotFoundException("JobSkill not found"));
            Response response = new Response(200, "Job Skill retrieved successfully", jobSkill);
            return ResponseEntity.ok(response);
        } catch (EntityIdNotFoundException e) {
            log.error("JobSkill not found: ", e);
            Response response = new Response(404, "JobSkill not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            log.error("Error retrieving JobSkill: ", e);
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<Response> getAll() {
        try {
            // Get Iterator from service
            Iterator<JobSkill> jobSkillIterator = jobSkillService.getAll();

            // Convert Iterator to List
            List<JobSkill> jobSkills = new ArrayList<>();
            jobSkillIterator.forEachRemaining(jobSkills::add);

            // Create response with converted data
            Response response = new Response(200, "Job Skills retrieved successfully", jobSkills);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving JobSkills: ", e);
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Find JobSkills by Skill ID
    @GetMapping("/by-skill/{skillId}")
    public ResponseEntity<Response> getBySkillId(@PathVariable Long skillId) {
        try {
            List<JobSkill> jobSkills = jobSkillService.findBySkillId(skillId);
            Response response = new Response(200, "Job Skills retrieved by Skill ID", jobSkills);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving JobSkills by Skill ID: ", e);
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Find JobSkills by Job ID
    @GetMapping("/by-job/{jobId}")
    public ResponseEntity<Response> getByJobId(@PathVariable Long jobId) {
        try {
            List<JobSkill> jobSkills = jobSkillService.findByJobId(jobId);
            Response response = new Response(200, "Job Skills retrieved by Job ID", jobSkills);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving JobSkills by Job ID: ", e);
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Find JobSkills by Skill Level
    @GetMapping("/by-skill-level/{skillLevel}")
    public ResponseEntity<Response> getBySkillLevel(@PathVariable SkillLevel skillLevel) {
        try {
            List<JobSkill> jobSkills = jobSkillService.findBySkillLevel(skillLevel);
            Response response = new Response(200, "Job Skills retrieved by Skill Level", jobSkills);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving JobSkills by Skill Level: ", e);
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
