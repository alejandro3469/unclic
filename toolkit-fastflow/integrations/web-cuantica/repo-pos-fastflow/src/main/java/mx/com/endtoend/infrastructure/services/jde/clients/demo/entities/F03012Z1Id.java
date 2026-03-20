package mx.com.endtoend.infrastructure.services.jde.clients.demo.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F03012Z1Id implements Serializable{

	private static final long serialVersionUID = 2437810154183501361L;
	
	private String voedus;

	private String voedbt;
	
	private String voedtn;
	
	private String voedln;
}