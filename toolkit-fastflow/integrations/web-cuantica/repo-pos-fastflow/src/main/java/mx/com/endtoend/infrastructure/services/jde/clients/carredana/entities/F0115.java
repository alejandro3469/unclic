package mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.carredana.CarredanaDataSourceConfigurationOracle;

@Data
@Entity
@Table(name = "F0115", schema = CarredanaDataSourceConfigurationOracle.dynamicSchema2)
public class F0115 {
	
	@EmbeddedId
	private F0115Id id;
	
	@Column(name = "wpar1")
	private String number;
	
	@Column(name = "wpph1")
	private String number1;

}
