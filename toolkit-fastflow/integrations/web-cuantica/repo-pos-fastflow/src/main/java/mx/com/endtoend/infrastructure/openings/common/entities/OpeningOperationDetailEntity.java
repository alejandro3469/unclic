package mx.com.endtoend.infrastructure.openings.common.entities;

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
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.entities.OpenPaymentInstrumentEntity;

@Entity
@Table(name = "opening_operation_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpeningOperationDetailEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private OpenPaymentInstrumentEntity openPaymentInstrument;

	@Column(name = "amount", nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private OpeningOperationEntity openingOperation;



}
