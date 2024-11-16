package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.JobSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.JobSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.JobSkillService;

import java.util.List;

@RestController
@RequestMapping("api/job-skill")
@Slf4j
public class JobSkillResources implements IResources<JobSkill, JobSkillId> {
    @Autowired
    private JobSkillService jss;
    @PostMapping
    @Override
    public ResponseEntity<Response> insert(@RequestBody JobSkill jobSkill) {
        return null;
    }

    @PostMapping("/list")
    @Override
    public ResponseEntity<Response> insertAll(@RequestBody List<JobSkill> list) {
        return null;
    }

    @Override
    @PutMapping
    public ResponseEntity<Response> update(JobSkillId jobSkillId, JobSkill jobSkill) {
        return null;
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Response> delete(JobSkillId jobSkillId) {
        return null;
    }

    @Override
    public ResponseEntity<Response> getById(JobSkillId jobSkillId) {
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return null;
    }

    @GetMapping("/jobs/{skillId}")
    public ResponseEntity<Response> getAllJobsBySkill(@PathVariable("skillId") Long skillId) {
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Get all jobs by skill id",
                jss.getAllJobsBySkill(skillId)
        ));
    }
}
