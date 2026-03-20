package mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "F4211")
@Data
public class F4211 {
	
	@EmbeddedId
	private F4211Id id;
	
	@Column(name = "SDITM") 
	private Long sditm;
	
	@Column(name = "SDLITM")
	private String sdlitm;
	
	@Column(name = "SDSOQS")
	private Long sdsoqs;
	
	@Column(name = "SDNXTR")
	private String sdnxtr;

	@Column(name = "SDLTTR")
	private String sdlttr;
		

}
