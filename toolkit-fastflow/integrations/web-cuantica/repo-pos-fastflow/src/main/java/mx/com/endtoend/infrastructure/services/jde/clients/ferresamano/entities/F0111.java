package mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.ferresamano.FerresamanoDataSourceConfigurationOracle;

@Data
@Entity
@Table(name = "F0111", schema = FerresamanoDataSourceConfigurationOracle.dynamicSchema2)
public class F0111 {

	@EmbeddedId
	private F0111Id id;
	
	private String wwmlnm;
	
	@Column(name = "wwnick")
	private String contact;
}
