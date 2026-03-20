package mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F4706Id implements Serializable{

	private static final long serialVersionUID = 8245623565947836419L;

	@Column(name = "ZAEKCO") 
	private String zaekco;
	
	@Column(name = "ZAEDOC") 
	private Long zaedoc;
	
	@Column(name = "ZAEDCT") 
	private String zaedct;
	
	@Column(name = "ZAEDLN") 
	private Long zaedln;
	
	@Column(name = "ZAFILE") 
	private String zafile;
	
	@Column(name = "ZAANTY") 
	private String zaanty;
	
}