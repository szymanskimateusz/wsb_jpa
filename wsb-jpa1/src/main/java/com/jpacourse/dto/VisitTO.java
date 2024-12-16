package com.jpacourse.dto;

import com.jpacourse.persistence.enums.TreatmentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VisitTO {
    private LocalDateTime time;
    private String doctorFullName;
    private List<TreatmentType> treatmentTypes;
}
