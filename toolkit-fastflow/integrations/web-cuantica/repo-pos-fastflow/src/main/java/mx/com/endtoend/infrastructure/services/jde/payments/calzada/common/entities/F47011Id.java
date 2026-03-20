package mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F47011Id implements Serializable{

	private static final long serialVersionUID = 3095907256832102475L;
	
	@Column(name = "SYEKCO")
	private String syekco;
	
	@Column(name = "SYEDOC")
	private Long syedoc;
	
	@Column(name = "SYEDCT")
	private String syedct;
}
