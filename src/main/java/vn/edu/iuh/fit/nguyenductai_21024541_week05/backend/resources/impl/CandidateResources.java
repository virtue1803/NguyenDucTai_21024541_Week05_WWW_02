package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto.CandidateAccountDTO;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.CandidateService;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/candidate")
@Slf4j
public class CandidateResources implements IResources<Candidate,Long> {

    @Autowired
    private CandidateService cs;
    @Override
    @PostMapping
    public ResponseEntity<Response> insert(@RequestBody Candidate candidate) {
        log.info(" inserting candidate");
        try {
            Candidate results = cs.add(candidate);
            log.info("Insert candidate successfully");
            return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "Insert candidate successfully", results));
        } catch (Exception e) {
            log.error("Insert candidate failed: " + e.getMessage());
            return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "Insert candidate failed!", null));
        } catch (Throwable e) {
            log.error("Insert candidate failed: " + e.getMessage());
            return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "Insert candidate failed!", null));
        }

    }

    @Override
    public ResponseEntity<Response> insertAll(List<Candidate> list) {
        return null;
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable("id") Long aLong,@RequestBody Candidate candidate) {
        return null;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long aLong) {
        return null;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable("id") Long aLong) {
        try {
            Optional<Candidate> opCan = cs.getById(aLong);
            return ResponseEntity.ok(new Response(
                    HttpStatus.OK.value(),
                    "Get candidate successfully",
                    opCan.get()
            ));
        } catch (EntityIdNotFoundException e) {
            log.warn("Get candidate failed for the candidate id not found!");
            return ResponseEntity.ok(new Response(
                    HttpStatus.NO_CONTENT.value(),
                    "The candidate id = " + aLong + " was not found!",
                    null
            ));
        } catch (Exception e) {
            log.error("Get candidate failed: " + e.getMessage());
            return ResponseEntity.ok(new Response(
                    HttpStatus.OK.value(),
                    "Get candidate failed!",
                    null
            ));
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Get all candidates successfully",
                cs.getAll()
        ));
    }


    @PostMapping("login")
    public ResponseEntity<Response> checkLoginAccount(@RequestBody CandidateAccountDTO caDto) {
        log.info("Calling check login account");
        String getEmail = caDto.getEmail();
        String getPassword = caDto.getPassword();

        try {
            Candidate output = cs.checkLoginAccount(getEmail, getPassword);
            if (output != null) {
                log.info("Check login account successfully");
                return ResponseEntity.ok(new Response(
                        HttpStatus.OK.value(),
                        "Check login account successfully",
                        output
                ));
            } else {
                log.warn("Check login account failed for the email or password is incorrect!");
                return ResponseEntity.ok(new Response(
                        HttpStatus.NO_CONTENT.value(),
                        "The email or password is incorrect!",
                        null
                ));
            }
        } catch (Exception e) {
            log.error("Check login account failed: " + e.getMessage());

            return ResponseEntity.ok(new Response(
                    HttpStatus.OK.value(),
                    "Check login account failed!",
                    null
            ));
        }
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Response> getAll(@PathVariable("page") String pageNumber) {
        Pageable page = (Pageable) PageRequest.of(Integer.parseInt(pageNumber), 10);
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Get all candidates successfully",
                cs.getAll(page)
        ));
    }

    @GetMapping("/{id}/skills")
    public ResponseEntity<Response> getCandidateSkills(@PathVariable("id") Long canId) {
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Get all candidate skills successfully",
                cs.getCandidateSkill(canId)
        ));
    }

    @GetMapping("/{id}/experiences")
    public ResponseEntity<Response> getCandidateExperience(@PathVariable("id") Long canId) {
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Get all candidate experiences successfully",
                cs.getCandidateExperience(canId)
        ));
    }
}
