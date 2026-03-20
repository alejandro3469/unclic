package mx.com.endtoend.infrastructure.articles.demo.oracle.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F4106Id implements Serializable{
	
	private static final long serialVersionUID = 9077671056249381691L;

	@Column(name="BPITM") 
	private BigDecimal bpitm;
	
	@Column(name="BPMCU") 
	private String bpmcu;
	
	@Column(name="BPLOCN") 
	private String bplocn;
	
	@Column(name="BPLOTN") 
	private String bplotn;
	
	@Column(name="BPAN8") 
	private BigDecimal bpan8;
	
	@Column(name="BPIGID") 
	private BigDecimal bpigid;
	
	@Column(name="BPCGID") 
	private BigDecimal bpcgid;
	
	@Column(name="BPLOTG") 
	private String bplotg;
	
	@Column(name="BPFRMP") 
	private BigDecimal bpfrmp;
	
	@Column(name="BPCRCD") 
	private String bpcrcd;
	
	@Column(name="BPUOM") 
	private String bpuom;
	
	@Column(name="BPEXDJ") 
	private BigDecimal bpexdj;
	
	@Column(name="BPUPMJ") 
	private BigDecimal bpupmj;
	
	@Column(name="BPTDAY") 
	private BigDecimal bptday;
}
