package mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F0111Z1Id implements Serializable{
	
	private static final long serialVersionUID = 2437810154183501361L;
	
	private String bwedus;
	
	private String bwedbt;
	
	private String bwedtn;
	
	private String bwedln;
}
