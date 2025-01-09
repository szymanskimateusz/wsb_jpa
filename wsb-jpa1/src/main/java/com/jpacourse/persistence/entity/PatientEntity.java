package com.jpacourse.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "PATIENT")
public class PatientEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "TELEPHONE_NUMBER", nullable = false)
	private String telephoneNumber;
	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "PATIENT_NUMBER", nullable = false)
	private String patientNumber;

	@Column(name = "DATE_OF_BIRTH", nullable = false)
	private LocalDate dateOfBirth;

	@Column(name = "ACTIVE", nullable = false) // nowo dodane pole, inne niż string
	private boolean active;

	@OneToMany( // jednostronna od rodzica (tu rodzic)
			cascade = CascadeType.ALL,
			mappedBy = "patient",
			orphanRemoval = true
	)
	private List<VisitEntity> visits;

	@OneToOne // dwustronna od rodzica (tu rodzic)
	@JoinColumn(name = "ADDRESS_ID", nullable = false)
	private AddressEntity address;
}
