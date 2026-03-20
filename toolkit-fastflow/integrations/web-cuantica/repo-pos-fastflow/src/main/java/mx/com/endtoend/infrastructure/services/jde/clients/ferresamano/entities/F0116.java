package mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.ferresamano.FerresamanoDataSourceConfigurationOracle;

@Data
@Entity
@Table(name = "F0116", schema = FerresamanoDataSourceConfigurationOracle.dynamicSchema2)
public class F0116 {

	@EmbeddedId
	private F0116Id id;

	@Column(name = "aladdz")
	private String cp;

	@Column(name = "alcty1")
	private String city;

	@Column(name = "aladd3")
	private String noOuNoIn;

	@Column(name = "aladd2")
	private String street;

	@Column(name = "aladd4")
	private String colony;

	@Column(name = "aladds")
	private String stateCode;

//	 @Column(name = "alctr")
//	 private String country;

	@Column(name = "aladd1")
	private String name1;

}
