package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.CandidateService;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.CandidateSkillService;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.CandidateSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto.CandidateSkillDTO;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.SkillService;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@RestController
@RequestMapping("/api/candidate-skills")
public class CandidateSkillResources implements IResources<CandidateSkillDTO, CandidateSkillId> {

    @Autowired
    private CandidateSkillService candidateSkillService;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private SkillService skillService;

    private CandidateSkillDTO convertToDTO(CandidateSkill candidateSkill) {
        Candidate candidate = candidateSkill.getId().getCandidate();
        Skill skill = candidateSkill.getId().getSkill();

        if (candidate == null || skill == null) {
            log.error("Candidate or Skill is null for CandidateSkill: {}", candidateSkill.getId());
        }

        // Trả về DTO với ID của Candidate và Skill thay vì đối tượng đầy đủ
        return new CandidateSkillDTO(
                candidate != null ? candidate.getId() : null,  // Chỉ lấy ID của Candidate
                skill != null ? skill.getId() : null,          // Chỉ lấy ID của Skill
                candidateSkill.getId(),                        // CandidateSkillId
                candidateSkill.getMoreInfos(),                 // Thông tin thêm
                candidateSkill.getSkillLevel()                  // Mức độ kỹ năng
        );
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

    @PutMapping
    public ResponseEntity<Response> update(
            @RequestParam("candidateId") Long candidateId,
            @RequestParam("skillId") Long skillId,
            @RequestBody CandidateSkillDTO candidateSkillDTO) {
        try {
            Candidate candidate = candidateService.getById(candidateId).orElseThrow(
                    () -> new EntityIdNotFoundException("Candidate not found")
            );
            Skill skill = skillService.getById(skillId).orElseThrow(
                    () -> new EntityIdNotFoundException("Skill not found")
            );

            CandidateSkillId id = new CandidateSkillId(candidate, skill);

            CandidateSkill candidateSkill = convertToEntity(candidateSkillDTO);
            candidateSkill.setId(id);

            CandidateSkill updatedSkill = candidateSkillService.update(candidateSkill);
            CandidateSkillDTO responseDTO = convertToDTO(updatedSkill);

            Response response = new Response(200, "Skill updated successfully", responseDTO);
            return ResponseEntity.ok(response);
        } catch (EntityIdNotFoundException e) {
            Response response = new Response(404, e.getMessage(), null);
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(500).body(response);
        }
    }


    @DeleteMapping
    public ResponseEntity<Response> delete(
            @RequestParam("candidateId") Long candidateId,
            @RequestParam("skillId") Long skillId) {
        try {
            Candidate candidate = candidateService.getById(candidateId).orElseThrow(
                    () -> new EntityIdNotFoundException("Candidate not found")
            );
            Skill skill = skillService.getById(skillId).orElseThrow(
                    () -> new EntityIdNotFoundException("Skill not found")
            );

            CandidateSkillId id = new CandidateSkillId(candidate, skill);

            candidateSkillService.delete(id);

            Response response = new Response(200, "Skill deleted successfully", null);
            return ResponseEntity.ok(response);
        } catch (EntityIdNotFoundException e) {
            Response response = new Response(404, e.getMessage(), null);
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            Response response = new Response(500, "Internal Server Error", null);
            return ResponseEntity.status(500).body(response);
        }
    }


//    @GetMapping("/get-by-id")
//    public ResponseEntity<Response> getById(
//            @RequestParam("canId") String candidateId,
//            @RequestParam("skillId") String skillId
//    ) {
//        try {
//            Candidate candidate = candidateService.getById(Long.parseLong(candidateId)).get();
//            Skill skill = skillService.getById(Long.parseLong(skillId)).get();
//            CandidateSkillId tr = new CandidateSkillId(candidate, skill);
//            // Lấy CandidateSkill từ service
//            CandidateSkill candidateSkill = candidateSkillService.getById(tr)
//                    .orElseThrow(() -> new EntityIdNotFoundException("Skill not found"));
//
//            // Chuyển đổi sang DTO với dữ liệu đầy đủ
//            CandidateSkillDTO responseDTO = convertToDTO(candidateSkill);
//
//            // Tạo phản hồi thành công
//            Response response = new Response(200, "Skill retrieved successfully", responseDTO);
//            return ResponseEntity.ok(response);
//        } catch (EntityIdNotFoundException e) {
//            // Trường hợp không tìm thấy dữ liệu
//            Response response = new Response(404, "Skill not found", null);
//            return ResponseEntity.status(404).body(response);
//        }
//    }

    @GetMapping("/{candidateId}/{skillId}")
    public ResponseEntity<Response> getById(
            @PathVariable Long candidateId,
            @PathVariable Long skillId) {
        try {
            // Tìm Candidate và Skill theo ID
            Candidate candidate = candidateService.getById(candidateId)
                    .orElseThrow(() -> new EntityIdNotFoundException("Candidate not found"));
            Skill skill = skillService.getById(skillId)
                    .orElseThrow(() -> new EntityIdNotFoundException("Skill not found"));

            // Tạo CandidateSkillId
            CandidateSkillId id = new CandidateSkillId(candidate, skill);

            // Tìm CandidateSkill theo ID
            CandidateSkill candidateSkill = candidateSkillService.getById(id)
                    .orElseThrow(() -> new EntityIdNotFoundException("CandidateSkill not found"));

            // Chuyển đổi sang DTO và trả về kết quả
            CandidateSkillDTO responseDTO = convertToDTO(candidateSkill);
            Response response = new Response(200, "Skill retrieved successfully", responseDTO);
            return ResponseEntity.ok(response);
        } catch (EntityIdNotFoundException e) {
            Response response = new Response(404, e.getMessage(), null);
            return ResponseEntity.status(404).body(response);
        }
    }




    @Override
    @GetMapping
    public ResponseEntity<Response> getAll() {
        Iterable<CandidateSkill> allSkills = () -> candidateSkillService.getAll();  // Convert Iterator to Iterable
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
