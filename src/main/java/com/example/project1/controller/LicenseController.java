package com.example.project1.controller;


import com.example.project1.entity.License;
import com.example.project1.service.LicenseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/licenses")
@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class LicenseController {

    private LicenseService licenseService;

    @GetMapping
    public ResponseEntity<List<License>> getAll(@RequestParam(value = "licenseName", required = false) String licenseName) {
        return ResponseEntity.ok(licenseService.getAll(licenseName));
    }


    public ResponseEntity<License> saveLicense(@RequestBody License license) {
        License selectedLicense = licenseService.insert(license);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("license", "/api/v1/license" + selectedLicense.getId().toString());
        return new ResponseEntity<>(selectedLicense, httpHeaders, HttpStatus.CREATED);
    }

    @PostMapping
    public Object insert(@RequestBody License license) {

        if (license.getStartTime() != null && license.getEndTime() != null) {
            licenseService.insert(license);
            return ResponseEntity.ok("Ok");
        } else if (license.getStartTime() == null || license.getEndTime() == null) {
            return ResponseEntity.ok("Without start time or end time you cannot create license");
        }
        return ResponseEntity.ok();
    }


    @PutMapping
    public ResponseEntity updateLicense(@RequestBody License license) {
        licenseService.updateLicense(license);
        return ResponseEntity.ok("OK");

    }

    @DeleteMapping({"/{licenseId}"})
    public ResponseEntity<String> deleteLicense(@PathVariable("licenseId") Long licenseId) {
        licenseService.deleteLicense(licenseId);
        return  ResponseEntity.ok("Deleted");
    }
}

