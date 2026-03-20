package mx.com.endtoend.infrastructure.articles.demo.oracle.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F4104Id implements Serializable {

	private static final long serialVersionUID = -3611405697836243246L;

	@Column(name = "IVAN8")
	private BigDecimal ivan8;
	
	@Column(name = "IVXRT")
	private String ivxrt;
	
	@Column(name = "IVITM")
	private BigDecimal ivitm;
	
	@Column(name = "IVEXDJ")
	private BigDecimal ivexdj;
	
	@Column(name = "IVCITM")
	private String ivcitm;
	
	@Column(name = "IVCIRV")
	private String ivcirv;
}
