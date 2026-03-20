package mx.com.endtoend.infrastructure.articles.calzada.oracle.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table (name = "F4008")
public class F4008 {

	@EmbeddedId
	private F4008Id id;
	
	@Column(name = "TATAXA") 
	private String tataxa;
	
	@Column(name = "TAEFTJ") 
	private BigDecimal taeftj;
	
	@Column(name = "TAGL01") 
	private String tagl01;
	
	@Column(name = "TATXR1") 
	private Integer tatxr1;
	
	@Column(name = "TAGL02") 
	private String tagl02;
	
	@Column(name = "TATXR2") 
	private Integer tatxr2;
	
	@Column(name = "TAGL03") 
	private String tagl03;
	
	@Column(name = "TATXR3") 
	private Integer tatxr3;
	
	@Column(name = "TAGL04") 
	private String tagl04;
	
	@Column(name = "TATXR4") 
	private Integer tatxr4;
	
	@Column(name = "TAGL05") 
	private String tagl05;
	
	@Column(name = "TATXR5") 
	private Integer tatxr5;
	
	@Column(name = "TATC2") 
	private String tatc2;
}
