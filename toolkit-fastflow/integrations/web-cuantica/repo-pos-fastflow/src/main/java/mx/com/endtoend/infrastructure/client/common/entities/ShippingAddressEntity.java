package mx.com.endtoend.infrastructure.client.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "shippingAddress")
public class ShippingAddressEntity {
	 @Id
	 @Column(name = "id", unique = true, nullable = false)
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @Column(name = "street", nullable = true)
	 private String street;
	 
	 @Column(name = "outdoor_number", nullable = true)
	 private String noOutdoor;
	 
	 @Column(name = "interior_number", nullable = true)
	 private String noInterior;
	 
	 @Column(name = "cp", nullable = true)
	 private String cp;
	 
	 @Column(name = "city", nullable = true)
	 private String city;
	 
	 @Column(name = "colony", nullable = true)
	 private String colony;
	 
	 @Column(name = "state_code", nullable = true)
	 private String stateCode;
	 
	 @Column(name = "delegation_code", nullable = true)
	 private String delegationCode;
	 
	 @Column(name = "flat_code", nullable = true)
	 private String flatCode;
	 
	 @Column(name = "coordinates_code", nullable = true)
	 private String coordinatesCode;
	 
	 @Column(name = "uvication_name", nullable = true)
	 private String uvicationName;
	 
	 @JoinColumn(name = "client_id")
	 @ManyToOne(optional = false, fetch = FetchType.EAGER)
	 private ClientEntity idClient;
	 
//	 @JoinColumn(name = "direction_id")
//	 @OneToOne(optional = false, fetch = FetchType.EAGER)
//	 private ClientDirectionEntity idDirection;
	 
}
