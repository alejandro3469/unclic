package mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F0116Id implements Serializable{
	
	private static final long serialVersionUID = 2437810154183501361L;
	
	@Column(name = "alan8")
	private Long noClient;

	private String aleftb;
}
