package mx.com.endtoend.infrastructure.articles.demo.oracle.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F41021Id implements Serializable{

	private static final long serialVersionUID = -6105987272295597927L;

	@Column(name="LIITM") 
	private BigDecimal liitm;
	
	@Column(name="LILOCN") 
	private String lilocn;
	
	@Column(name="LILOTN") 
	private String lilotn;
	
	@Column(name="LIMCU") 
	private String limcu;
}
