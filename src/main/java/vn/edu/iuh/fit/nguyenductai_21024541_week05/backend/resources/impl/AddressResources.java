package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Address;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.AddressService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/address")
@Slf4j
public class AddressResources implements IResources<Address,Long> {

    @Autowired
    private AddressService as;

    @PostMapping
    @Override
    public ResponseEntity<Response> insert(@RequestBody Address address) {
        log.info(" inserting address");
        try {
            Address results = as.add(address);
            log.info("Insert address successfully");
            return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "Insert address successfully", results));
        } catch (Exception e) {
            log.error("Insert address failed" + e.getMessage());
            return ResponseEntity.ok(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Insert address failed!", null));
        }
    }

    @Override
    public ResponseEntity<Response> insertAll(List<Address> list) {
        return null;
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable("id") Long aLong,@RequestBody Address address) {
        return null;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(Long aLong) {
        return null;
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Response> getById(@PathVariable("id") Long aLong) {
        log.info("Calling get address by id = " + aLong);
        try {
            Optional<Address> address = as.getById(aLong);
            log.info("Get address by id successfully");
            return ResponseEntity.ok(new Response(
                    HttpStatus.OK.value(),
                    "Get address by id successfully",
                    address.get()
            ));
        } catch (EntityIdNotFoundException e) {
            log.warn("The address id = " + aLong + " not found");
            return ResponseEntity.ok(new Response(
                    HttpStatus.OK.value(),
                    "The address id = " + aLong + " not found",
                    null
            ));
        } catch (Exception e) {
            log.error("Get address by id failed");
            log.error("Error: ", e);
            return ResponseEntity.ok(new Response(
                    HttpStatus.OK.value(),
                    "Get address by id failed",
                    null
            ));
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<Response> getAll() {
        log.info("Calling get all addresses");
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Get all address successfully",
                as.getAll()
        ));
    }
}
