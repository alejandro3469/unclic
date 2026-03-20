package mx.com.endtoend.infrastructure.services.posLegacy.clients.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "DirEnvioCliente")
public class ShippingAddressSqlEntity {
	
	 @Id
	 @Column(name = "NoCliente")
	 private Long noClient;
	 
	 @Column(name = "Calle")
	 private String street;
	 
	 @Column(name = "NoExterior")
	 private String noOutdoor;
	 
	 @Column(name = "NoInterior")
	 private String noInterior;
	 
	 @Column(name = "CodigoPostal")
	 private String cp;
	 
	 @Column(name = "Ciudad")
	 private String city;
	 
	 @Column(name = "Colonia")
	 private String colony;
	 
	 @Column(name = "Estado")
	 private String stateCode;
	 
	 @Column(name = "Delegacion")
	 private String delegationCode;
	 
	 @Column(name = "Plano")
	 private String flatCode;
	 
	 @Column(name = "Coordenada")
	 private String coordinatesCode;
	 


}
