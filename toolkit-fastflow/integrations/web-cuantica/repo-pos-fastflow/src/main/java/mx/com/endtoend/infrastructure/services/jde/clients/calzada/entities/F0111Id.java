package mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F0111Id implements Serializable{
	
	private static final long serialVersionUID = 2437810154183501361L;

	@Column(name = "wwan8")
	private Long noClient;
	
	private String wwidln;

}
