package mx.com.endtoend.infrastructure.payments.common.entities;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "check_payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CheckPaymentEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "line", nullable = false)
	private int line;

	@Column(name = "payment_id", nullable = false)
	private Long paymentId;
	
	@Column(name = "currency", nullable = false)
	private String currency;

	@Column(name = "exchange_rate", nullable = false, precision = 19, scale = 2)
	private BigDecimal exchangeRate;
	
	@Column(name = "banking_institution", nullable = false)
	private String bankingInstitution;

	@Column(name = "check_number", nullable = false)
	private String checkNumber;

	@Column(name = "amount_applied", nullable = false, precision = 19, scale = 2)
	private BigDecimal amountApplied;
	
}
