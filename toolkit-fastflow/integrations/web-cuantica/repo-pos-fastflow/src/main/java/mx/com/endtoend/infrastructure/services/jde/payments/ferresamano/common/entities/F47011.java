package mx.com.endtoend.infrastructure.services.jde.payments.ferresamano.common.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.ferresamano.FerresamanoDataSourceConfigurationOracle;

@Entity
@Table(name = "F47011", schema = FerresamanoDataSourceConfigurationOracle.dynamicSchema2)
@Data
public class F47011 {
	
	@EmbeddedId
	private F47011Id id;

	@Column(name = "SYEDTY") 
	private String syedty;
	
	@Column(name = "SYEDLN") 
	private Long syedln;
	
	@Column(name = "SYEDER") 
	private String syeder;
	
	@Column(name = "SYEDSP") 
	private String syedsp;
	
	@Column(name = "SYEDBT") 
	private String syedbt;
	
	@Column(name = "SYKCOO") 
	private String sykcoo;
	
	@Column(name = "SYDCTO") 
	private String sydcto;
	
	@Column(name = "SYMCU") 
	private String symcu;
	
	@Column(name = "SYOKCO") 
	private String syokco;
	
	@Column(name = "SYOORN") 
	private String syoorn;
	
	@Column(name = "SYOCTO") 
	private String syocto;
	
	@Column(name = "SYRKCO") 
	private String syrkco;
	
	@Column(name = "SYRORN") 
	private String syrorn;
	
	@Column(name = "SYRCTO") 
	private String syrcto;

	@Column(name = "SYAN8") 
	private Long syan8;
	
	@Column(name = "SYSHAN") 
	private Long syshan;
	
	@Column(name = "SYDRQJ") 
	private Long sydrqj;
	
	@Column(name = "SYTRDJ") 
	private Long sytrdj;
	
	@Column(name = "SYVR01") 
	private String syvr01;
	
	@Column(name = "SYASN") 
	private String syasn;
	
	@Column(name = "SYSTOP") 
	private String systop;
	
	@Column(name = "SYZON") 
	private String syzon;
	
	@Column(name = "SYCRRM") 
	private String sycrrm;
	
	@Column(name = "SYCRCD") 
	private String sycrcd;
	
	@Column(name = "SYCRR") 
	private Long sycrr;
	
	@Column(name = "SYTORG") 
	private String sytorg;
	
	@Column(name = "SYUSER") 
	private String syuser;
	
	@Column(name = "SYPID") 
	private String sypid;
	
	@Column(name = "SYJOBN") 
	private String syjobn;
	
	@Column(name = "SYUPMJ") 
	private Long syupmj;
	
	@Column(name = "SYHOLD") 
	private String syhold;
	
	@Column(name = "SYVR02") 
	private String syvr02;
	
	@Column(name = "SYDOCO") 
	private Long sydoco;
	
	@Column(name = "SYEXR1") 
	private String syexr1;
	
	@Column(name = "SYTXA1") 
	private String sytxa1;
	
	@Column(name = "SYPA8") 
	private Long sypa8;
	
	@Column(name = "SYPTC") 
	private String syptc;
	
	@Column(name = "SYRYIN") 
	private String syryin;
	
	@Column(name = "SYRCD") 
	private String syrcd;
	
	@Column(name = "SYURRF") 
	private String syurrf;
	
}

