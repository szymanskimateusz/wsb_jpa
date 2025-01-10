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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    final Logger logger = LoggerFactory.getLogger(PatientDaoTest.class);

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



    @Test
    @Transactional
    public void shouldFindPatientsByLastName() {
        PatientEntity patient = createTestPatients("Krolikowali", LocalDate.of(2000,1,1), false);
        PatientEntity patient2 = createTestPatients("Inny", LocalDate.of(2004,1,1), true);

        List<PatientEntity> result = patientDao.findPatientsByLastName("Krolikowali");

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(p -> p.getLastName().equals("Krolikowali"));
    }

    @Test
    @Transactional
    public void shouldFindPatientsWhoHadMoreVisitsThanX() {
        VisitEntity savedVisit = createTestVisit();
        VisitEntity savedVisit2 = createTestVisit();

        List<PatientEntity> result = patientDao.findPatientsWhoHadMoreVisitsThanX(1L);

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(p -> p.getVisits().size() > 1L);
    }

    @Test
    @Transactional
    public void shouldFindPatientsByDateOfBirthBetween() {
        PatientEntity patient = createTestPatients("Krolikowali", LocalDate.of(2000,1,1), false);
        PatientEntity patient2 = createTestPatients("Inny", LocalDate.of(2004,1,1), true);

        LocalDate after = LocalDate.of(1999,1,1);
        LocalDate before = LocalDate.of(2001,1,2);

        List<PatientEntity> result = patientDao.findPatientsByDateOfBirthBetween(after, before);

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(p -> p.getDateOfBirth().isAfter(after));
        assertThat(result).allMatch(p -> p.getDateOfBirth().isBefore(before));
    }

    @Test
    @Transactional
    public void shouldFindPatientsbyActivityStatus() {
        PatientEntity patient = createTestPatients("Krolikowali", LocalDate.of(2000,1,1), false);
        PatientEntity patient2 = createTestPatients("Inny", LocalDate.of(2004,1,1), true);

        List<PatientEntity> result = patientDao.findPatientsbyActivityStatus(false);

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(p -> !p.isActive());
    }

    @Test
    @Transactional
    public void shouldThrowOptimisticLockExceptionOnConcurrentModification() throws InterruptedException {

        PatientEntity patient = createTestPatients("Krolikowali", LocalDate.of(2000,1,1), false);

        entityManager.persist(patient);
        entityManager.flush();
        entityManager.clear();

        Runnable task1 = createTask(patient.getId(), "Mateusz");
        Runnable task2 = createTask(patient.getId(), "Jan");

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    private Runnable createTask(Long patientId, String newFirstName) {
        return () -> {
            EntityManager threadLocalEM = entityManagerFactory.createEntityManager();
            try {
                threadLocalEM.getTransaction().begin();
                PatientEntity patient = threadLocalEM.find(PatientEntity.class, patientId);
                logger.info("Thread version: " + patient.getVersion());
                patient.setFirstName(newFirstName);
                threadLocalEM.merge(patient);
                threadLocalEM.flush();
                threadLocalEM.getTransaction().commit();
                logger.info("Thread updated version: " + patient.getVersion());
            } catch (OptimisticLockException e) {
                threadLocalEM.getTransaction().rollback();
                logger.error("Thread OptimisticLockException", e);
            } catch (Exception e) {
                threadLocalEM.getTransaction().rollback();
                logger.error("Thread exception:", e);
            } finally {
                threadLocalEM.close();
            }
        };
    }

    @Transactional
    public PatientEntity createTestPatients(String tLastName, LocalDate tDateOfBirth, boolean tIsActive) {
        AddressEntity address = new AddressEntity();
        address.setCity("Testowy");
        address.setPostalCode("10-100");
        address.setAddressLine1("Testowa 1");
        address.setAddressLine2("A");
        addressDao.save(address);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Jan");
        patient.setLastName(tLastName);
        patient.setTelephoneNumber("123123123");
        patient.setEmail("dsadas@fds.fds");
        patient.setPatientNumber("1");
        patient.setDateOfBirth(tDateOfBirth);
        patient.setVisits(new ArrayList<>());
        patient.setAddress(address);
        patient.setActive(tIsActive);
        patientDao.save(patient);

        return patient;
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
        address2.setCity("Testowyyy");
        address2.setPostalCode("10-150");
        address2.setAddressLine1("Testowa 2");
        address2.setAddressLine2("B");
        addressDao.save(address2);

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

        savedPatient.getVisits().add(visit);
        visitDao.save(visit);

        return visit;
    }
}
