package mx.com.endtoend.infrastructure.articles.ferresamano.oracle.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.ferresamano.FerresamanoDataSourceConfigurationOracle;

@Entity
@Data
@Table(name = "F41002", schema = FerresamanoDataSourceConfigurationOracle.dynamicSchema2)
public class F41002 {

	@EmbeddedId
	private F41002Id id;

	@Column(name = "UMCONV")
	private BigDecimal umconv;

	@Column(name = "UMEXSO")
	private String umexso;

}

