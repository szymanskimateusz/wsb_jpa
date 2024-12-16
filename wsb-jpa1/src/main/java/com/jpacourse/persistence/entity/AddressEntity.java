package com.jpacourse.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ADDRESS")
public class AddressEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "CITY", nullable = false)
	private String city;
	@Column(name = "ADDRESS_LINE1", nullable = false)
	private String addressLine1;
	@Column(name = "ADDRESS_LINE2", nullable = false)
	private String addressLine2;
	@Column(name = "POSTAL_CODE", nullable = false)
	private String postalCode;
}
