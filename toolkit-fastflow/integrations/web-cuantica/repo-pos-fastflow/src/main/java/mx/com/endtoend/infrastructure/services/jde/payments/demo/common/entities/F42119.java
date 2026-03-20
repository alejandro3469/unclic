package mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.demo.DemoDataSourceConfigurationOracle;

@Entity
@Table(name = "F42119", schema = DemoDataSourceConfigurationOracle.dynamicSchema2)
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
	
	@Column(name = "SDUORG")
	private Long sduorg;
	
	@Column(name = "SDPDDJ")
	private Long sdpddj;
	
	@Column(name = "SDADDJ")
	private Long sdaddj;
	
	@Column(name = "SDIVD")
	private String sdivd;
	
	@Column(name = "SDUOM")
	private String sduom;

}
