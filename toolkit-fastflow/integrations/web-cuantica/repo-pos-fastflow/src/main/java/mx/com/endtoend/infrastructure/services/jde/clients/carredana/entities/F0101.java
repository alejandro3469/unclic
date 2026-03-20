package mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.carredana.CarredanaDataSourceConfigurationOracle;

@Data
@Entity
@Table(name = "F0101", schema = CarredanaDataSourceConfigurationOracle.dynamicSchema2)
public class F0101 {
	
	 @Id
	 @Column(name = "aban8")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long noClient;
	 
	 @Column(name = "abtax")
	 private String rfc;
	 
	 @Column(name = "abalph")
	 private String name;

	 @Column(name = "abtaxc")
	 private String taxpayer;
	 
	 private String abat1;
}
