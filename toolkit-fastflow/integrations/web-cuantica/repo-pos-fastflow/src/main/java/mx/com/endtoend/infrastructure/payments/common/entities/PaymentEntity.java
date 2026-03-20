package mx.com.endtoend.infrastructure.payments.common.entities;

import java.math.BigDecimal;
import java.util.Date;

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
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;

	@Column(name = "opening_cash_id", nullable = false)
	private Long openingCashId;

	@Column(name = "payment_date", nullable = false)
	private Date paymentDate;

	@Column(name = "client_number", nullable = false)
	private Long clientNumber;

	@Column(name = "employee_email", nullable = false)
	private String employeeEmail;

	@Column(name = "user_number", nullable = false)
	private Long userNumber;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "payment_state", nullable = false)
	private String paymentState;

	@Column(name = "order_number", nullable = false)
	private BigDecimal orderNumber;

	@Column(name = "order_code", nullable = false)
	private String orderCode;

	@Column(name = "order_total", nullable = false, precision = 19, scale = 2)
	private BigDecimal orderTotal;

	@Column(name = "pending_payment", nullable = false, precision = 19, scale = 2)
	private BigDecimal pendingPayment;

	@Column(name = "is_printed", nullable = false)
	private boolean isPrinted;

}
