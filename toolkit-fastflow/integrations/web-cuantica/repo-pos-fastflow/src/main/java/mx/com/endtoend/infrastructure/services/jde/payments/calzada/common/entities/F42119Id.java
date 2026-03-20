package mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class F42119Id implements Serializable {

	private static final long serialVersionUID = -532294512177197527L;

	@Column(name = "SDKCOO")
	private String sdkcoo;

	@Column(name = "SDDOCO")
	private BigDecimal sddoco;

	@Column(name = "SDDCTO")
	private String sddcto;

	@Column(name = "SDLNID")
	private Long sdlnid;
}
