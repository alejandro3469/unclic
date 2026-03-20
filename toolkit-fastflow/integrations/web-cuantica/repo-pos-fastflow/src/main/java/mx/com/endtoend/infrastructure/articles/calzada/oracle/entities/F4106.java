package mx.com.endtoend.infrastructure.articles.calzada.oracle.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "F4106")
public class F4106 {
	
	@EmbeddedId
	private F4106Id id;
	
	@Column(name="BPEFTJ") 
	private BigDecimal bpeftj;
	
	@Column(name="BPUPRC") 
	private BigDecimal bpuprc;
}
