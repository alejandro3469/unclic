package mx.com.endtoend.infrastructure.services.jde.clients.demo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F01151Id implements Serializable{
	
	private static final long serialVersionUID = 2437810154183501361L;
	
	@Column(name = "eaan8")
	private Long noClient;
	
	private String eaidln;
	
	private String earck7;

}
