package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class TblDetalleOrdenId implements Serializable{

	
	private static final long serialVersionUID = 924967527061659963L;
	
	@Column(name = "DOCO")
	private BigDecimal doco;
	
	@Column(name = "DCTO")
	private String dcto;
	
	@Column(name = "IDLINEA")
	private double idLinea;
	
	@Column(name = "MCUVENTA")
	private String mcuVenta;
	

}
