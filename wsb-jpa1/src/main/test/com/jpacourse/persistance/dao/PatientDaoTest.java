package com.jpacourse.persistance.dao;

import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import com.jpacourse.repository.AddressRepository;
import com.jpacourse.repository.DoctorRepository;
import com.jpacourse.repository.PatientRepository;
import com.jpacourse.repository.VisitRepository;
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
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    @Transactional
    public void shouldAddVisitToPatient(){
        AddressEntity address = new AddressEntity();
        address.setCity("Testowy");
        address.setPostalCode("10-100");
        address.setAddressLine1("Testowa 1");
        address.setAddressLine2("A");

        AddressEntity savedAddress = addressRepository.save(address);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Jan");
        patient.setLastName("Krolikowali");
        patient.setTelephoneNumber("123123123");
        patient.setEmail("dsadas@fds.fds");
        patient.setPatientNumber("1");
        patient.setDateOfBirth(LocalDate.of(2000,1,1));
        patient.setVisits(new ArrayList<>());
        patient.setAddress(savedAddress);
        patient.setActive(false);

        PatientEntity savedPatient = patientRepository.save(patient);

        AddressEntity address2 = new AddressEntity();
        address2.setCity("Testowy");
        address2.setPostalCode("10-100");
        address2.setAddressLine1("Testowa 2");
        address2.setAddressLine2("B");

        AddressEntity savedAddress2 = addressRepository.save(address2);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Batosz");
        doctor.setLastName("Kol");
        doctor.setTelephoneNumber("123123571");
        doctor.setEmail("batkol@test.com");
        doctor.setDoctorNumber("Dbk1");
        doctor.setAddress(savedAddress2);
        doctor.setSpecialization(Specialization.OCULIST);

        DoctorEntity savedDoctor = doctorRepository.save(doctor);

        LocalDateTime visitDate = LocalDateTime.now().plusDays(1);
        String description = "Example";
        patientDao.addVisitToPatient(savedPatient.getId(), savedDoctor.getId(), visitDate, description);

        PatientEntity updatedPatient = patientRepository.findById(savedPatient.getId()).orElse(null);

        assertThat(updatedPatient).isNotNull();
        assertThat(updatedPatient.getVisits()).hasSize(1);

        VisitEntity visit = savedPatient.getVisits().get(0);

        assertThat(visit.getTime()).isEqualTo(visitDate);
        assertThat(visit.getDescription()).isEqualTo(description);
        assertThat(visit.getPatient().getId()).isEqualTo(updatedPatient.getId());
        assertThat(visit.getDoctor().getId()).isEqualTo(savedDoctor.getId());

        VisitEntity savedVisit = visitRepository.findById(visit.getId()).orElse(null);

        assertThat(savedVisit).isNotNull();
        assertThat(savedVisit.getDescription()).isEqualTo(description);
    }
}
