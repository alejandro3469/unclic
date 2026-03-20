package mx.com.endtoend.infrastructure.articles.carredana.oracle.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.carredana.CarredanaDataSourceConfigurationOracle;

@Entity
@Data
@Table (name = "F4104", schema = CarredanaDataSourceConfigurationOracle.dynamicSchema2)
public class F4104 {
	
	@EmbeddedId
	private F4104Id id;
	
	@Column(name = "IVMCU")
	private String ivmcu;
	
	@Column(name = "IVLITM")
	private String ivlitm;

}
