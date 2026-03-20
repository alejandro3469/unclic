package mx.com.endtoend.infrastructure.services.posLegacy.payments.common.entities;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "CobroEfectivo")
@Data
public class CobroEfectivo {

	@Id
	private UUID IdCobroEfectivo;
	
	private Double MontoMN;
	
	private BigDecimal DOCO;
	
	private String DCTO;
	
	private String ModifiedBy;
}
