package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class DirEnvioClienteOrdenesId implements Serializable{

	private static final long serialVersionUID = 457376771291399834L;
	
	@Column(name = "DOCO")
	private BigDecimal doco;
	
	@Column(name = "DCTO")
	private String dcto;

	@Column(name = "NoCliente")
	private double noCliente;
	
}
