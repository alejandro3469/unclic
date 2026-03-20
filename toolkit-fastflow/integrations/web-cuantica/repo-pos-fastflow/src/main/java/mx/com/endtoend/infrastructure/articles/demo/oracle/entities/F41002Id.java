package mx.com.endtoend.infrastructure.articles.demo.oracle.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F41002Id implements Serializable {

	private static final long serialVersionUID = 1434485991878913210L;

	@Column(name = "UMMCU")
	private String ummcu;

	@Column(name = "UMITM")
	private BigDecimal umitm;

	@Column(name = "UMUM")
	private String umum;

	@Column(name = "UMRUM")
	private String umrum;

}
