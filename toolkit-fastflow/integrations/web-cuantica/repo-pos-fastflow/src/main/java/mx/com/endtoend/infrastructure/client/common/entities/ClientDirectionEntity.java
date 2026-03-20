package mx.com.endtoend.infrastructure.client.common.entities;


import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "clients_direction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDirectionEntity {

	 @Id
	 @Column(name = "id", unique = true, nullable = true)
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	 @Column(name = "cp", nullable = true)
	 private String cp;
	 
	 @Column(name = "state_code", nullable = true)
	 private String stateCode;
 
	 @Column(name = "outdoor_number", nullable = true)
	 private String noOutdoor;
	 
	 @Column(name = "street", nullable = true)
	 private String street;
	 
	 @Column(name = "delegation_code", nullable = true)
	 private String delegationCode;
	 
	 @Column(name = "interior_number", nullable = true)
	 private String noInterior;
	 
	 @Column(name = "city", nullable = true)
	 private String city;
	 
	 @Column(name = "flat_code", nullable = true)
	 private String flatCode;

	 @Column(name = "colony", nullable = true)
	 private String colony;
	 
	 @Column(name = "coordinates_code", nullable = true)
	 private String coordinatesCode;
	 
//	 @Column(name = "uvication_name", nullable = true)
//	 private String uvicationName;
	 
	 @JoinColumn(name = "client_id")
	 @ManyToOne(optional = false, fetch = FetchType.EAGER)
	 private ClientEntity idClient;
	 

}
