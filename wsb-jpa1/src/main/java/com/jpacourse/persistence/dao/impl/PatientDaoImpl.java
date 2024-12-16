package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private VisitRepository visitRepository;

    @Transactional
    @Override
    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitTime, String description) {
        PatientEntity patient = findOne(patientId);
        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);

        VisitEntity visit = new VisitEntity();
        visit.setDescription(description);
        visit.setTime(visitTime);
        visit.setPatient(patient);
        visit.setDoctor(doctor);

        visitRepository.save(visit);

        patient.getVisits().add(visit);

        update(patient);
    }
}
