package com.jpacourse.service;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PatientServiceTest {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void shouldRemoveVisitWhenPatientIsRemoved() {
        VisitEntity savedVisit = createTestVisit();
        Long patientId = savedVisit.getPatient().getId();
        Long visitId = savedVisit.getId();

        patientRepository.deleteById(patientId);

        Optional<PatientEntity> patientEntity = patientRepository.findById(patientId);
        assertTrue(patientEntity.isEmpty(), "Patient was removed");

        Optional<VisitEntity> visitEntity = visitRepository.findById(visitId);
        assertTrue(visitEntity.isEmpty(), "Visit was removed");
    }

    @Transactional
    public VisitEntity createTestVisit() {
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
        patient.setEmail("dsfsd@fds.fsd");
        patient.setPatientNumber("1");
        patient.setDateOfBirth(LocalDate.of(2000,1,1));
        patient.setVisits(new ArrayList<>());
        patient.setActive(false);
        patient.setAddress(savedAddress);

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
        doctor.setSpecialization(Specialization.OCULIST);
        doctor.setAddress(savedAddress2);

        DoctorEntity savedDoctor = doctorRepository.save(doctor);

        VisitEntity visit = new VisitEntity();
        visit.setDescription("example");
        visit.setTime(LocalDateTime.now());
        visit.setPatient(savedPatient);
        visit.setDoctor(savedDoctor);

        savedPatient.getVisits().add(visit);

        return visitRepository.save(visit);
    }
}
