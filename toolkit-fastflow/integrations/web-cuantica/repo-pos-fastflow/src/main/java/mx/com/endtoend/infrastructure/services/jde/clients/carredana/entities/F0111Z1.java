package mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.carredana.CarredanaDataSourceConfigurationOracle;

@Entity
@Data
@Table(name = "F0111Z1", schema = CarredanaDataSourceConfigurationOracle.dynamicSchema2)
public class F0111Z1 {
	
	@EmbeddedId
	private F0111Z1Id id;
	
	private String bwtytn;
	
	private String bweddt;
	
	private String bwdrin;
	
	private String bweddl;
	
	private String bwedsp;
	
	private String bwtnac;
	
	@Column(name = "bwan8")
	private Long noClient;
	
	private String bwidln;
	
	private String bwedct;
	
	private String bwdss5;
	
	private String bwmlnm;
	
	private String bwalph;
	
	private String bwtyc;
	
	private String bwuser;
	
	private String bwjobn;
	
	private String bwntyp;
	
	private String bwchproc;
	
	private String bwslnm;
	
	private String bwnick;

}
