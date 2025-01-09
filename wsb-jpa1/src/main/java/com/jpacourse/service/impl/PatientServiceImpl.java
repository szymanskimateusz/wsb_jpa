package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private AddressDao addressDao;

    @Autowired
    private PatientDao patientDao;

    @Override
    public Optional<PatientTO> getPatientById(Long id) {
        return Optional.ofNullable(patientDao.findOne(id))
                .map(PatientMapper::toTO);
    }

    @Override
    public PatientTO createPatient(PatientTO patientTO) {
        AddressEntity addressEntity = (AddressEntity) addressDao.findByAddress(
                patientTO.getAddress().getCity(),
                patientTO.getAddress().getAddressLine1(),
                patientTO.getAddress().getPostalCode()
        );

        PatientEntity patientEntity = PatientMapper.toEntity(patientTO);
        patientEntity.setAddress(addressEntity);

        PatientEntity savedEntity = patientDao.save(patientEntity);

        return PatientMapper.toTO(savedEntity);
    }

    @Override
    public boolean deletePatient(Long id) {
        return false;
    }
}
