package com.jpacourse.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

	@OneToOne(mappedBy = "addressEntity") // dwustronna od rodzica (tu dziecko)
	private PatientEntity patientEntity;

	@OneToOne(mappedBy = "addressEntity") // dwustronna od rodzica (tu dziecko)
	private DoctorEntity doctorEntity;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "CITY", nullable = false)
	private String city;
	@Column(name = "ADDRESS_LINE1", nullable = false)
	private String addressLine1;
	@Column(name = "ADDRESS_LINE2", nullable = false)
	private String addressLine2;
//	@Transient
	@Column(name = "POSTAL_CODE", nullable = false)
	private String postalCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}
