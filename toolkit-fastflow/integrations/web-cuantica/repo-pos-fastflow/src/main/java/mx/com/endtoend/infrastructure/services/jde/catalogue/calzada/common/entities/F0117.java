package mx.com.endtoend.infrastructure.services.jde.catalogue.calzada.common.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.calzada.CalzadaDataSourceConfigurationOracle;

@Entity
@Data
@Table (name = "F0117", schema = CalzadaDataSourceConfigurationOracle.dynamicSchema2)
public class F0117 {

	@EmbeddedId
	private F0117Id id;
	
	private String a8adds;
	
	private String a8ctr;
	
}
