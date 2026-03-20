package mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.carredana.CarredanaDataSourceConfigurationOracle;

@Entity
@Data
@Table (name = "F0005", schema = CarredanaDataSourceConfigurationOracle.dynamicSchema)
public class F0005 {

	@EmbeddedId
	private F0005Id id;
	
	private String drdl01;
	
	private String drdl02;
	
	private String drsphd;
	
	public String getDrdl01() {
 	return this.drdl01.trim();
	}
	
	public String getDrdl02() {
	 	return this.drdl02.trim();
	}

//	@Override
//	public String toString() {
//		return "F0005 [id=" + id + ", drdl01=" + drdl01 + "]";
//	}
}
