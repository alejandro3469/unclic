package mx.com.endtoend.infrastructure.articles.ferresamano.oracle.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.ferresamano.FerresamanoDataSourceConfigurationOracle;

@Entity
@Data
@Table(name = "F4102", schema = FerresamanoDataSourceConfigurationOracle.dynamicSchema2)
public class F4102 {
	@EmbeddedId
	private F4102Id id;
	
	@Column(name = "IBAITM")
	private String ibaitm;
	
	@Column(name = "IBVEND")
	private Integer ibvend;
	
	@Column(name = "IBPRGR")
	private String ibprgr;
	
	@Column(name = "IBLNTY")
	private String iblnty;
	
	@Column(name = "IBTAX1")
	private String ibtax1;
	
	@Column(name = "IBSTKT")
	private String ibstkt;

}

