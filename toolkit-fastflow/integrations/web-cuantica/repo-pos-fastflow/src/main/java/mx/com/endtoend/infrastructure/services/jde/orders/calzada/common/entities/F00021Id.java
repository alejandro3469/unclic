package mx.com.endtoend.infrastructure.services.jde.orders.calzada.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F00021Id implements Serializable{

	private static final long serialVersionUID = -5781429528910307722L;
	
	@Column(name = "NLKCO") 
	private String nlkco;
	
	@Column(name = "NLDCT") 
	private String nldct;
	
	
}
