package mx.com.endtoend.infrastructure.services.jde.catalogue.demo.common.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F0005Id implements Serializable{

	private static final long serialVersionUID = 2437810154183501361L;

	private String drsy;
	
    private String drrt;
    
    private String drky;
    
	public String getDrsy() {
	 	return this.drsy.trim();
	}
	
	public String getDrrt() {
	 	return this.drrt.trim();
	}
	
	public String getDrky() {
	 	return this.drky.trim();
	}
    
	@Override
	public String toString() {
		return "F0005Id [drsy=" + drsy + ", drrt=" + drrt + ", drky=" + drky + "]";
	}
     
 
}
