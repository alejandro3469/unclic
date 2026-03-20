package mx.com.endtoend.infrastructure.services.jde.clients.demo.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.demo.DemoDataSourceConfigurationOracle;

@Data
@Entity
@Table(name = "F01151Z1", schema = DemoDataSourceConfigurationOracle.dynamicSchema2)
public class F01151Z1 {

	@EmbeddedId
	private F01151Z1Id id;
	
	@Column(name = "ebemal")
	private String email;
	
	@Column(name = "eban8")
	private String noClient;
	
	private String ebtytn;
	
	private String ebeddt;
	
	private String ebdrin;
	
	private String ebeddl;
	
	private String ebedsp;
	
	private String ebtnac;
	
	private String ebidln;
	
	private String ebrck7;
	
	private String ebetp;
	
	private String ebuser;
	
	private String ebpid;
	
	private String ebupmj;
	
	private String ebjobn;
	
	private String ebtday;
	
	private String ebupmt;
	
	private String ebehier;
	
	private String ebcfno1;
	
	private String ebedct;
	
	private String ebedft;
	
}
