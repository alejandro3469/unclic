package mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.carredana.CarredanaDataSourceConfigurationOracle;

@Entity
@Table(name = "F42119", schema = CarredanaDataSourceConfigurationOracle.dynamicSchema2)
@Data
public class F42119 {
	
	@EmbeddedId
	private F42119Id id;
	
	@Column(name = "SDITM") 
	private Long sditm;
	
	@Column(name = "SDLITM")
	private String sdlitm;
	
	@Column(name = "SDSOQS")
	private Long sdsoqs;
	
	@Column(name = "SDNXTR")
	private String sdnxtr;

	@Column(name = "SDLTTR")
	private String sdlttr;

}
