package com.jpacourse.persistance.dao;

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

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class PatientDaoTest {
    @Autowired
    private PatientDao patientDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private VisitDao visitDao;
    @Autowired
    private AddressDao addressDao;

    @Test
    @Transactional
    public void shouldAddVisitToPatient(){
        AddressEntity address = new AddressEntity();
        address.setCity("Testowy");
        address.setPostalCode("10-100");
        address.setAddressLine1("Testowa 1");
        address.setAddressLine2("A");
        addressDao.save(address);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Jan");
        patient.setLastName("Krolikowali");
        patient.setTelephoneNumber("123123123");
        patient.setEmail("dsadas@fds.fds");
        patient.setPatientNumber("1");
        patient.setDateOfBirth(LocalDate.of(2000,1,1));
        patient.setVisits(new ArrayList<>());
        patient.setAddress(address);
        patient.setActive(false);
        patientDao.save(patient);

        AddressEntity address2 = new AddressEntity();
        address2.setCity("Testowy");
        address2.setPostalCode("10-100");
        address2.setAddressLine1("Testowa 2");
        address2.setAddressLine2("B");
        addressDao.save(address2);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Batosz");
        doctor.setLastName("Kol");
        doctor.setTelephoneNumber("123123571");
        doctor.setEmail("batkol@test.com");
        doctor.setDoctorNumber("Dbk1");
        doctor.setAddress(address2);
        doctor.setSpecialization(Specialization.OCULIST);
        doctorDao.save(doctor);

        LocalDateTime visitDate = LocalDateTime.now().plusDays(1);
        String description = "Example";
        patientDao.addVisitToPatient(patient.getId(), doctor.getId(), visitDate, description);

        PatientEntity updatedPatient = patientDao.findOne(patient.getId());

        assertThat(updatedPatient).isNotNull();
        assertThat(updatedPatient.getVisits()).hasSize(1);

        VisitEntity visit = updatedPatient.getVisits().get(0);

        assertThat(visit.getTime()).isEqualTo(visitDate);
        assertThat(visit.getDescription()).isEqualTo(description);
        assertThat(visit.getPatient().getId()).isEqualTo(updatedPatient.getId());
        assertThat(visit.getDoctor().getId()).isEqualTo(doctor.getId());

        VisitEntity savedVisit = visitDao.findOne(visit.getId());

        assertThat(savedVisit).isNotNull();
        assertThat(savedVisit.getDescription()).isEqualTo(description);
    }
}
