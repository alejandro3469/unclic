package mx.com.endtoend.infrastructure.services.jde.payments.ferresamano.common.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.ferresamano.FerresamanoDataSourceConfigurationOracle;

@Entity
@Table(name = "F4211", schema = FerresamanoDataSourceConfigurationOracle.dynamicSchema2)
@Data
public class F4211 {
	
	@EmbeddedId
	private F4211Id id;
	
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

