package mx.com.endtoend.infrastructure.closings.common.entities;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.entities.ClosePaymentInstrumentEntity;

@Entity
@Table(name = "closing_operation_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClosingOperationDetailEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private ClosePaymentInstrumentEntity closePaymentInstrument;
	
	@Column(name = "amount", nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ClosingOperationEntity closingOperation;
	
}
