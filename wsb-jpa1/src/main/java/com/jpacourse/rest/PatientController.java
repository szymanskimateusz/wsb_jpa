package com.jpacourse.rest;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientTO> getPatientById(@PathVariable Long id) {
        Optional<PatientTO> patientTO = patientService.getPatientById(id);
        return patientTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PatientTO> createPatient(@RequestBody PatientTO patientTO) {
        PatientTO createdPatient = patientService.createPatient(patientTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        if (patientService.deletePatient(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/visits/{id}")
    public ResponseEntity<List<VisitTO>> findVisitsByPatientId(@PathVariable Long id) {
        List<VisitTO> visitTO = patientService.findVisitsByPatientId(id);
        return visitTO.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(visitTO);
    }
}
