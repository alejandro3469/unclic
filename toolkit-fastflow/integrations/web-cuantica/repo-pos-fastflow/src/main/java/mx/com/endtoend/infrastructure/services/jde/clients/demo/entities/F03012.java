package mx.com.endtoend.infrastructure.services.jde.clients.demo.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.demo.DemoDataSourceConfigurationOracle;

@Data
@Entity
@Table(name = "F03012", schema = DemoDataSourceConfigurationOracle.dynamicSchema2)
public class F03012 {
	
	@EmbeddedId
	private F03012Id id;
	
	@Column(name = "aitxa1")
	private String iva;

	public String getIva() {
	 	return this.iva.trim();
	}
}
