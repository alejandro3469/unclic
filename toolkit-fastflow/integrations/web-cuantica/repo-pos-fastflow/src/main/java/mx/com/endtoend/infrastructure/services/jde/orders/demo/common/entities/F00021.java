package mx.com.endtoend.infrastructure.services.jde.orders.demo.common.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.demo.DemoDataSourceConfigurationOracle;

@Entity
@Table(name = "F00021", schema = DemoDataSourceConfigurationOracle.dynamicSchema)
@Data
public class F00021 {

	@EmbeddedId
	private F00021Id id;
	
	@Column(name = "NLN001") 
	private BigDecimal nln001;
	

}

