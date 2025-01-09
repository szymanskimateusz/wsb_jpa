package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.PatientEntity;

import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public class PatientMapper {
    public static PatientTO toTO(PatientEntity patientEntity) {
        if (patientEntity == null)
            return null;

        PatientTO patientTO = new PatientTO();
        patientTO.setId(patientEntity.getId());
        patientTO.setFirstName(patientEntity.getFirstName());
        patientTO.setLastName(patientEntity.getLastName());
        patientTO.setTelephoneNumber(patientEntity.getTelephoneNumber());
        patientTO.setEmail(patientEntity.getEmail());
        patientTO.setPatientNumber(patientEntity.getPatientNumber());
        patientTO.setDateOfBirth(patientEntity.getDateOfBirth());
        patientTO.setAddress(AddressMapper.toTO(patientEntity.getAddress()));
        patientTO.setActive(patientEntity.isActive());

        patientTO.setVisits(patientEntity.getVisits() != null ? patientEntity.getVisits().stream().map(VisitMapper::toTO).collect(Collectors.toList()) : emptyList());

        return patientTO;
    }

    public static PatientEntity toEntity(PatientTO patientTO) {
        if (patientTO == null)
            return null;

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientTO.getId());
        patientEntity.setFirstName(patientTO.getFirstName());
        patientEntity.setLastName(patientTO.getLastName());
        patientEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
        patientEntity.setEmail(patientTO.getEmail());
        patientEntity.setPatientNumber(patientTO.getPatientNumber());
        patientEntity.setDateOfBirth(patientTO.getDateOfBirth());
        patientEntity.setActive(patientTO.isActive());

        AddressEntity addressEntity = AddressMapper.toEntity(patientTO.getAddress());
        patientEntity.setAddress(addressEntity);

        return patientEntity;
    }
}
