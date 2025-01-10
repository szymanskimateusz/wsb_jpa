package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PatientDao extends Dao<PatientEntity, Long> {
    void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitTime, String description);

    List<PatientEntity> findPatientsByLastName(String pLastName);
    List<PatientEntity> findPatientsWhoHadMoreVisitsThanX(Long x);
    List<PatientEntity> findPatientsByDateOfBirthBetween(LocalDate startDate, LocalDate endDate); // spełnia zad. 4 przez użycie between
    List<PatientEntity> findPatientsbyActivityStatus(boolean isActive); // spełnia zad. 4 przez użycie własnej dodanej zmiennej
}
