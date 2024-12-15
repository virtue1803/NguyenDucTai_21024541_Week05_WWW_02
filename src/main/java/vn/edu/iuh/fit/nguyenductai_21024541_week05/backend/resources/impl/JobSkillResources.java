package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto.JobDTO;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto.JobSkillDTO;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto.SkillDTO;
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
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/job-skills")
public class JobSkillResources implements IResources<JobSkill, JobSkillId> {

    @Autowired
    private JobSkillService jobSkillService;

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

//    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable JobSkillId id, @RequestBody JobSkill jobSkill) {
        try {
            jobSkill.setId(id);
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

//    @Override
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

//    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable JobSkillId id) {
        try {
            JobSkill jobSkill = jobSkillService.getById(id).orElseThrow(() -> new EntityIdNotFoundException("JobSkill not found"));
            Response response = new Response(200, "Job Skill found", jobSkill);
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
    @GetMapping("/all")
    public ResponseEntity<Response> getAll() {
        try {
            Iterator<JobSkill> jobSkills = jobSkillService.getAll();
            List<JobSkillDTO> jobSkillDTOs = new ArrayList<>();
            while (jobSkills.hasNext()) {
                JobSkill jobSkill = jobSkills.next();
                jobSkillDTOs.add(new JobSkillDTO(
                        new JobDTO(
                                jobSkill.getId().getJob().getId(),
                                jobSkill.getId().getJob().getJobName(),
                                jobSkill.getId().getJob().getJobDesc(),
                                jobSkill.getId().getJob().getCompany().getCompName()
                        ),
                        new SkillDTO(
                                jobSkill.getId().getSkill().getId(),
                                jobSkill.getId().getSkill().getSkillDescription(),
                                jobSkill.getId().getSkill().getSkillName(),
                                jobSkill.getId().getSkill().getType()
                        ),
                        jobSkill.getMoreInfos(),
                        jobSkill.getSkillLevel()
                ));
            }
            Response response = new Response(200, "Job Skills found", jobSkillDTOs);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving Job Skills: ", e);
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/skill/{skillId}")
    public ResponseEntity<Response> findBySkillId(@PathVariable Long skillId) {
        try {
            List<JobSkill> jobSkills = jobSkillService.findBySkillId(skillId);
            if (jobSkills.isEmpty()) {
                Response response = new Response(404, "No Job Skills found for Skill ID " + skillId, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Response response = new Response(200, "Job Skills found for Skill ID " + skillId, jobSkills);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error finding Job Skills by Skill ID: ", e);
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/skill-level/{skillLevel}")
    public ResponseEntity<Response> findBySkillLevel(@PathVariable SkillLevel skillLevel) {
        try {
            List<JobSkill> jobSkills = jobSkillService.findBySkillLevel(skillLevel);
            if (jobSkills.isEmpty()) {
                Response response = new Response(404, "No Job Skills found for Skill Level " + skillLevel, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Response response = new Response(200, "Job Skills found for Skill Level " + skillLevel, jobSkills);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error finding Job Skills by Skill Level: ", e);
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<Response> findByJobId(@PathVariable Long jobId) {
        try {
            List<JobSkill> jobSkills = jobSkillService.findByJobId(jobId);
            if (jobSkills.isEmpty()) {
                Response response = new Response(404, "No Job Skills found for Job ID " + jobId, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Response response = new Response(200, "Job Skills found for Job ID " + jobId, jobSkills);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error finding Job Skills by Job ID: ", e);
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
