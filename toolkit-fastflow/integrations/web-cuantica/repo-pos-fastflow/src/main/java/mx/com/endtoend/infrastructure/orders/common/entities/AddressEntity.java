package mx.com.endtoend.infrastructure.orders.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orders_addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "street", unique = false, nullable = false)
	private String street;
	
	@Column(name = "colony", unique = false, nullable = false)
	private String colony;
	
	@Column(name = "delegation", unique = false, nullable = false)
	private String delegation;
	
	@Column(name = "delegation_code", unique = false, nullable = false)
	private String delegationCode;
	
	@Column(name = "interior_number", unique = false, nullable = true)
	private String interiorNumber;
	
	@Column(name = "outdoor_number", unique = false, nullable = true)
	private String outdoorNumber;

	@Column(name = "cp", unique = false, nullable = false)
	private String cp;
	
	@Column(name = "state", unique = false, nullable = false)
	private String state;
	
	@Column(name = "state_code", unique = false, nullable = false)
	private String stateCode;

	@Column(name = "city", unique = false, nullable = false)
	private String city;
	
	@Column(name = "address_type", unique = false, nullable = false)
	private String addressType;
	
	@Column(name = "flat", unique = false, nullable = true)
	private String flat;

	@Column(name = "flat_code", unique = false, nullable = true)
	private String flatCode;

	@Column(name = "coordinate", unique = false, nullable = true)
	private String coordinate;

	@Column(name = "coordinate_code", unique = false, nullable = true)
	private String coordinateCode;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private OrderEntity order;
}
