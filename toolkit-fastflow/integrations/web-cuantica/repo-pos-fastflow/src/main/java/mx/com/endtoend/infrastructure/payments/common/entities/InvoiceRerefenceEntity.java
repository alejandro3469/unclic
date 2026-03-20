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
@Table(name = "invoice_references")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InvoiceRerefenceEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "payment_id", nullable = false)
	private Long paymentId;

	@Column(name = "invoice_number", nullable = false)
	private BigDecimal invoiceNumber;

	@Column(name = "invoice_code", nullable = false)
	private String invoiceCode;

	@Column(name = "order_number", nullable = false)
	private BigDecimal orderNumber;

	@Column(name = "order_code", nullable = false)
	private String orderCode;
	
	@Column(name = "branch_code", nullable = false)
	private String branchCode;
	
	@Column(name = "migrated", nullable = false)
	private boolean isMigrated;

}
