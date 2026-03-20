package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class TblDetalleOrdenBkupId implements Serializable{

	private static final long serialVersionUID = 2538642203731409362L;

	@Column(name = "DOCO")
	private BigDecimal doco;

	@Column(name = "DCTO")
	private String dcto;

	@Column(name = "IDLINEA")
	private double idLinea;

	@Column(name = "MCUVENTA")
	private String mcuVenta;

}
