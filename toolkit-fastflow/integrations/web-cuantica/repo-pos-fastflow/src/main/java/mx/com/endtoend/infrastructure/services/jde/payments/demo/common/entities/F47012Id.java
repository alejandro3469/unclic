package mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F47012Id implements Serializable{
	
	private static final long serialVersionUID = 3879807781978086546L;
	
	
	@Column(name = "SZEKCO")
	private String szekco;
	
	@Column(name = "SZEDOC")
	private Long szedoc;
	
	@Column(name = "SZEDCT")
	private String szedct;
	
	@Column(name = "SZEDLN")
	private Long szedln;

}
