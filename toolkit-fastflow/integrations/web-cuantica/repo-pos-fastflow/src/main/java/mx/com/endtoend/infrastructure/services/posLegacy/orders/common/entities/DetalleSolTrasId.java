package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class DetalleSolTrasId implements Serializable {

	private static final long serialVersionUID = -3839590936394069270L;

	@Column(name = "DOCO")
	private BigDecimal doco;

	@Column(name = "DCTO")
	private String dcto;

	@Column(name = "KCOOO")
	private String kcooo;

	@Column(name = "MCU")
	private String mcu;

	@Column(name = "IDLINEA")
	private float idLinea;

}
