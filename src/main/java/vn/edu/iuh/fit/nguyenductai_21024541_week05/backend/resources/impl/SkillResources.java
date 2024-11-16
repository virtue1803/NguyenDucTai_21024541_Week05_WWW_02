package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.SkillService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/skill")
@Slf4j
public class SkillResources implements IResources<Skill,Long> {

    @Autowired
    private SkillService ss;
    @PostMapping
    @Override
    public ResponseEntity<Response> insert(@RequestBody Skill skill) {
        try {
            Skill results = ss.add(skill);
            log.info("Insert skill successfully");
            return ResponseEntity.ok(new Response(
                    HttpStatus.OK.value(),
                    "Insert skill successfully",
                    results
            ));
        } catch (Exception e) {
            log.error("Insert skill failed: " + e.getMessage());

            return ResponseEntity.ok(new Response(
                    HttpStatus.OK.value(),
                    "Insert skill failed!",
                    null
            ));
        }
    }

    @Override
    public ResponseEntity<Response> insertAll(List<Skill> list) {
        return null;
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Response> update(@PathVariable("id") Long aLong, @RequestBody Skill skill) {
        return null;
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Response> delete(@PathVariable("id") Long aLong) {
        return null;
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Response> getById(@PathVariable("id") Long aLong) {
        log.info("Calling get skill by id = " + aLong);
        try {
            Optional<Skill> opCan = ss.getById(aLong);
            return ResponseEntity.ok(new Response(
                    HttpStatus.OK.value(),
                    "Get skill successfully",
                    opCan.get()
            ));
        } catch (EntityIdNotFoundException e) {
            log.warn("Get skill failed for the skill id not found!");
            return ResponseEntity.ok(new Response(
                    HttpStatus.NO_CONTENT.value(),
                    "The skill id = " + aLong + " was not found!",
                    null
            ));
        } catch (Exception e) {
            log.error("Get skill failed: "+ e.getMessage());

            return ResponseEntity.ok(new Response(
                    HttpStatus.OK.value(),
                    "Get skill failed!",
                    null
            ));
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Get all skill successfully",
                ss.getAll()
        ));
    }
}
