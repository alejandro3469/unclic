package mx.com.endtoend.infrastructure.articles.calzada.oracle.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "F41021")
public class F41021 implements Serializable{

	private static final long serialVersionUID = 3070944030512402545L;

	@EmbeddedId
	private F41021Id id;
	
	@Column(name="LIPQOH") 
	private BigDecimal lipqoh;
	
	@Column(name="LIHCOM") 
	private BigDecimal lihcom; 
	
	@Column(name="LIPCOM") 
	private BigDecimal lipcom; 
	
	@Column(name="LIFCOM") 
	private BigDecimal lifcom; 

	@Column(name="LIPREQ") 
	private BigDecimal lipreq; 
	
	@Column(name="LIOT1P") 
	private BigDecimal liot1p;
	
	@Column(name="LIQOWO") 
	private BigDecimal liqowo; 
	
	@Column(name="LIPID")
	private String lipid;
	
	@Column(name="LIUPMJ")
	private Long liupmj;

	@Column(name="LIUSER")
	private String liuser;
	
	@Column(name="LITDAY")
	private Long litday;
}
