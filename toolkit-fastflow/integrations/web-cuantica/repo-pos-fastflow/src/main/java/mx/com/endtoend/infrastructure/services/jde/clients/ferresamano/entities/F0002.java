package mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.ferresamano.FerresamanoDataSourceConfigurationOracle;

@Data
@Entity
@Table(name = "F0002", schema = FerresamanoDataSourceConfigurationOracle.dynamicSchema)
public class F0002 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String nnsy;

	private String nnn001;

}
