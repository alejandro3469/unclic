package mx.com.endtoend.infrastructure.articles.calzada.oracle.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table (name = "F4104")
public class F4104 {
	
	@EmbeddedId
	private F4104Id id;
	
	@Column(name = "IVMCU")
	private String ivmcu;
	
	@Column(name = "IVLITM")
	private String ivlitm;

}
