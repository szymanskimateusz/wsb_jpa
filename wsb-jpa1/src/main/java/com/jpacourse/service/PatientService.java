package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.VisitEntity;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    Optional<PatientTO> getPatientById(Long id);
    PatientTO createPatient(PatientTO patientTO);
    boolean deletePatient(Long id);
    List<VisitTO> findVisitsByPatientId(Long id);
}
