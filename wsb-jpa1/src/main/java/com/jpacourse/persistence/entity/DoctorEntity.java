package com.jpacourse.persistence.entity;

import com.jpacourse.persistence.enums.Specialization;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "DOCTOR")
public class DoctorEntity {
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

	@Column(name = "DOCTOR_NUMBER", nullable = false)
	private String doctorNumber;

	@Column(name = "SPECIALIZATION", nullable = false)
	@Enumerated(EnumType.STRING)
	private Specialization specialization;

	@OneToMany(	// jednostronna od rodzica (tu rodzic)
			cascade = CascadeType.ALL,
			mappedBy = "doctor"
	)
	private List<VisitEntity> visits;

	@OneToOne	// dwustronna od rodzica (tu rodzic)
	@JoinColumn(name = "ADDRESS_ID", nullable = false)
	private AddressEntity address;

}
