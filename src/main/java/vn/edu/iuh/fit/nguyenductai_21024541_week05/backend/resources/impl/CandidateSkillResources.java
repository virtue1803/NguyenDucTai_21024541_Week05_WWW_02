package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.CandidateSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.CandidateSkillService;

import java.util.List;

@RestController
@RequestMapping("api/candidate-skill")
@Slf4j
public class CandidateSkillResources implements IResources<CandidateSkill, CandidateSkillId> {

    @Autowired
    private CandidateSkillService css;
    @Override
    @PostMapping
    public ResponseEntity<Response> insert(@RequestBody CandidateSkill candidateSkill) {
        return null;
    }

    @PostMapping("/list")
    @Override
    public ResponseEntity<Response> insertAll(@RequestBody List<CandidateSkill> list) {
        return null;
    }

    @PutMapping
    @Override
    public ResponseEntity<Response> update(CandidateSkillId candidateSkillId, CandidateSkill candidateSkill) {
        return null;
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Response> delete(CandidateSkillId candidateSkillId) {
        return null;
    }

    @Override
    public ResponseEntity<Response> getById(CandidateSkillId candidateSkillId) {
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return null;
    }
}
