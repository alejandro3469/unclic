package mx.com.endtoend.infrastructure.services.posLegacy.payments.common.entities;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "CobroTransferencia")
@Data
public class CobroTransferencia {

	@Id
	private UUID IdCobroTransferencia;
	
	private Double MontoMN;
	
	private BigDecimal DOCO;
	
	private String DCTO;
	
	private String ModifiedBy;
}
