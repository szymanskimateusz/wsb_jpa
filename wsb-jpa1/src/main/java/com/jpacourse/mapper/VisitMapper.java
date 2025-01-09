package com.jpacourse.mapper;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.entity.VisitEntity;

import java.util.stream.Collectors;

public class VisitMapper {
    public static VisitTO toTO(VisitEntity visitEntity) {
        if (visitEntity == null)
            return null;

        VisitTO visitTO = new VisitTO();

        visitTO.setDoctorFullName(visitEntity.getDoctor().getFirstName()+" "+visitEntity.getDoctor().getLastName());
        visitTO.setTime(visitEntity.getTime());
        visitTO.setTreatmentTypes(visitEntity.getMedicalTreatments().stream().map(MedicalTreatmentEntity::getType).collect(Collectors.toList()));

        return visitTO;
    }
}
