package mx.com.endtoend.infrastructure.articles.carredana.oracle.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F4008Id implements Serializable{

	private static final long serialVersionUID = 5564570715433033116L;
	
	@Column(name = "TATXA1") 
	private String tatxa1;
	
	@Column(name = "TAEFDJ") 
	private BigDecimal taefdj;
	
	@Column(name = "TAITM") 
	private BigDecimal taitm;

}
