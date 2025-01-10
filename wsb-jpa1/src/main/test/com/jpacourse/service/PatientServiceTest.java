package com.jpacourse.service;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.dao.VisitDao;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PatientServiceTest {
    @Autowired
    private PatientDao patientDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private VisitDao visitDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private PatientService patientService;

    @Test
    @Transactional
    public void shouldRemoveVisitWhenPatientIsRemoved() {
        VisitEntity savedVisit = createTestVisit();
        Long patientId = savedVisit.getPatient().getId();
        Long visitId = savedVisit.getId();

        patientDao.delete(savedVisit.getPatient());

        Optional<PatientEntity> patientEntity = Optional.ofNullable(patientDao.findOne(patientId));
        assertTrue(patientEntity.isEmpty(), "Patient was removed");

        Optional<VisitEntity> visitEntity = Optional.ofNullable(visitDao.findOne(visitId));
        assertTrue(visitEntity.isEmpty(), "Visit was removed");
    }

    @Test
    @Transactional
    public void shouldFindVisitsByPatientId() {
        VisitEntity savedVisit = createTestVisit();
        VisitEntity savedVisit2 = createTestVisit();
        Long patientId = savedVisit.getPatient().getId();

        List<VisitTO> result = patientService.findVisitsByPatientId(patientId);

        assertFalse(result.isEmpty(), "List of visits was found");
    }

    @Transactional
    public VisitEntity createTestVisit() {
        AddressEntity address = new AddressEntity();
        address.setCity("Testowy");
        address.setPostalCode("10-100");
        address.setAddressLine1("Testowa 1");
        address.setAddressLine2("A");

        AddressEntity savedAddress = addressDao.save(address);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Jan");
        patient.setLastName("Krolikowali");
        patient.setTelephoneNumber("123123123");
        patient.setEmail("dsfsd@fds.fsd");
        patient.setPatientNumber("1");
        patient.setDateOfBirth(LocalDate.of(2000,1,1));
        patient.setVisits(new ArrayList<>());
        patient.setActive(false);
        patient.setAddress(savedAddress);

        PatientEntity savedPatient = patientDao.save(patient);

        AddressEntity address2 = new AddressEntity();
        address2.setCity("Testowy");
        address2.setPostalCode("10-100");
        address2.setAddressLine1("Testowa 2");
        address2.setAddressLine2("B");

        AddressEntity savedAddress2 = addressDao.save(address2);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Batosz");
        doctor.setLastName("Kol");
        doctor.setTelephoneNumber("123123571");
        doctor.setEmail("batkol@test.com");
        doctor.setDoctorNumber("Dbk1");
        doctor.setSpecialization(Specialization.OCULIST);
        doctor.setAddress(savedAddress2);

        DoctorEntity savedDoctor = doctorDao.save(doctor);

        VisitEntity visit = new VisitEntity();
        visit.setDescription("example");
        visit.setTime(LocalDateTime.now());
        visit.setPatient(savedPatient);
        visit.setDoctor(savedDoctor);
        visit.setMedicalTreatments(new ArrayList<>());

        savedPatient.getVisits().add(visit);
        visitDao.save(visit);

        return visit;
    }
}
