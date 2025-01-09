package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;

import java.util.Optional;

public interface PatientService {
    Optional<PatientTO> getPatientById(Long id);
    PatientTO createPatient(PatientTO patientTO);
    boolean deletePatient(Long id);
}
