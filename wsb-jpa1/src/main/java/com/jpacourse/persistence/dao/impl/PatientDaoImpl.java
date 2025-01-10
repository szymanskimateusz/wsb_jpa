package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {
    @Autowired
    private DoctorDao doctorDao;

    @Transactional
    @Override
    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitTime, String description) {
        PatientEntity patient = findOne(patientId);
        DoctorEntity doctor = doctorDao.findOne(doctorId);
        VisitEntity visit = new VisitEntity();
        visit.setDescription(description);
        visit.setTime(visitTime);
        visit.setPatient(patient);
        visit.setDoctor(doctor);

        patient.getVisits().add(visit);

        update(patient);
    }

    @Override
    public List<PatientEntity> findPatientsByLastName(String pLastName) {
        return entityManager.createQuery("select p from PatientEntity p where p.lastName = :pLastName", PatientEntity.class)
                .setParameter("pLastName", pLastName)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWhoHadMoreVisitsThanX(Long x) {
        return entityManager.createQuery("select p from PatientEntity p left join p.visits v " +
                " group by p.id, p.firstName, p.lastName having count(v.patient) > :x", PatientEntity.class)
                .setParameter("x", x)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsByDateOfBirthBetween(LocalDate startDate, LocalDate endDate) {
        return entityManager.createQuery("select p from PatientEntity p " +
                        " where p.dateOfBirth between :startDate and :endDate", PatientEntity.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsbyActivityStatus(boolean isActive) {
        return entityManager.createQuery("select p from PatientEntity p where p.active = :isActive", PatientEntity.class)
                .setParameter("isActive", isActive)
                .getResultList();
    }
}
