package com.jpacourse.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "VISIT")
public class VisitEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Lob
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TIME")
	private LocalDateTime time;

	@ManyToOne
	@JoinColumn(name="DOCTOR_ID", nullable = false)
	private DoctorEntity doctor;

	@ManyToOne
	@JoinColumn(name="PATIENT_ID", nullable = false)
	private PatientEntity patient;

	@OneToMany( // jednostronna od rodzica (tu rodzic)
			cascade = CascadeType.ALL
	)
	@JoinColumn(name = "VISIT_ID")
	private List<MedicalTreatmentEntity> medicalTreatments;
}
