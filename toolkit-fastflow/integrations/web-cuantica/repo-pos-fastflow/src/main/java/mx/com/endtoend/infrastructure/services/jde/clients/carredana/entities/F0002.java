package mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.carredana.CarredanaDataSourceConfigurationOracle;

@Data
@Entity
@Table(name = "F0002", schema = CarredanaDataSourceConfigurationOracle.dynamicSchema)
public class F0002 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String nnsy;

	private String nnn001;

}
