package mx.com.endtoend.infrastructure.services.jde.clients.demo.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.demo.DemoDataSourceConfigurationOracle;

@Data
@Entity
@Table(name = "F01151", schema = DemoDataSourceConfigurationOracle.dynamicSchema2)
public class F01151 {

	@EmbeddedId
	private F01151Id id;
	
	@Column(name = "eaemal")
	private String mail;
	
}
