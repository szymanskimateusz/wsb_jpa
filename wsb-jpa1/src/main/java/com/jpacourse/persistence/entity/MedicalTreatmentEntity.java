package com.jpacourse.persistence.entity;

import com.jpacourse.persistence.enums.TreatmentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "MEDICAL_TREATMENT")
public class MedicalTreatmentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Column(name = "TREATMENT_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private TreatmentType type;



}
