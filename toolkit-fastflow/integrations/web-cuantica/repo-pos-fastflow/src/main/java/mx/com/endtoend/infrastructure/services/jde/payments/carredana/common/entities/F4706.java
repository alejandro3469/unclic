package mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.carredana.CarredanaDataSourceConfigurationOracle;

@Entity
@Table(name = "F4706", schema = CarredanaDataSourceConfigurationOracle.dynamicSchema2)
@Data
public class F4706 {
	
	@EmbeddedId
	private F4706Id id;
	
	@Column(name = "ZAEDTY") 
	private String zaedty;
	
	@Column(name = "ZAEDSQ") 
	private Long zaedsq;
	
	@Column(name = "ZAEDSP") 
	private String zaedsp;
	
	@Column(name = "ZADOCO") 
	private BigDecimal zadoco;
	
	@Column(name = "ZADCTO") 
	private String zadcto;
	
	@Column(name = "ZAKCOO") 
	private String zakcoo;
	
	@Column(name = "ZAAN8") 
	private Long zaan8;
	
	@Column(name = "ZAMLNM") 
	private String zamlnm;
	
	@Column(name = "ZAADD1") 
	private String zaadd1;
	
	@Column(name = "ZAADD2") 
	private String zaadd2;
	
	@Column(name = "ZAADD3") 
	private String zaadd3;
	
	@Column(name = "ZAADD4") 
	private String zaadd4;
	
	@Column(name = "ZAADDZ") 
	private String zaaddz;
	
	@Column(name = "ZACTY1") 
	private String zacty1;
	
	@Column(name = "ZACOUN") 
	private String zacoun;
	
	@Column(name = "ZAADDS") 
	private String zaadds;
	
	@Column(name = "ZACRTE") 
	private String zacrte;
	
	@Column(name = "ZABKML") 
	private String zabkml;
	
	@Column(name = "ZACTR") 
	private String zactr;
	
	@Column(name = "ZATORG") 
	private String zatorg;
	
	@Column(name = "ZAUSER") 
	private String zauser;
	
	@Column(name = "ZAPID") 
	private String zapid;
	
	@Column(name = "ZAJOBN") 
	private String zajobn;
	
	@Column(name = "ZAUPMJ") 
	private Long zaupmj;
	
	@Column(name = "ZATDAY") 
	private Long zatday;
	
	@Column(name = "ZALNID") 
	private Long zalnid;
	
	@Column(name = "ZAGAN8") 
	private Long zagan8;

}