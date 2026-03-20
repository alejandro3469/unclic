package mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F4714Id implements Serializable{
	
	private static final long serialVersionUID = -4656752981841896839L;

	@Column(name = "ZTEKCO") 
	private String ztekco;
	
	@Column(name = "ZTEDOC")
	private Long ztedoc;
	
	@Column(name = "ZTEDCT")
	private String ztedct;
	
	@Column(name = "ZTEDLN")
	private Long ztedln;
	
	@Column(name = "ZTFILE")
	private String ztfile;
	
	@Column(name = "ZTLINS")
	private Long ztlins;
	
}
