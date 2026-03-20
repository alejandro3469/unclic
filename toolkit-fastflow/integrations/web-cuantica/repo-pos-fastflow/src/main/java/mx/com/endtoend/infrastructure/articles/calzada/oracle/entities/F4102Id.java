package mx.com.endtoend.infrastructure.articles.calzada.oracle.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F4102Id implements Serializable{

	private static final long serialVersionUID = -277947101552799904L;

	@Column(name = "IBITM")
	private BigDecimal ibitm;
	
	@Column(name = "IBMCU")
	private String ibmcu;
}
