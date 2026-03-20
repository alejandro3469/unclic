package mx.com.endtoend.infrastructure.logs.orders.entities;

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

@Entity
@Table(name = "order_final_state_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFinalStateEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@Column(name = "order_summary_id", nullable = false)
	private Long orderSummaryId;

	@Column(name = "order_number")
	private BigDecimal orderNumber;

	@Column(name = "batch_folio")
	private Long batchFolio;

	@Column(name = "order_code")
	private String orderCode;

	@Column(name = "branch_code")
	private String branchCode;

	@Column(name = "company_number")
	private String companyNumber;

	@Column(name = "currency")
	private String currency;

	@Column(name = "exchangeRate")
	private BigDecimal exchangeRate;

	@Column(name = "creating_date")
	private Date creationDate;

	@Column(name = "request_date")
	private Date requestDate;

	@Column(name = "validity_date")
	private Date validityDate;

	@Column(name = "client_summary", length = 600)
	private String clientSummary;

	@Column(name = "client_Tax")
	private BigDecimal clientTax;

	@Column(name = "client_reference")
	private String clientReference;

	@Column(name = "user_number")
	private Long userNumber;

	@Column(name = "id_user")
	private Long idUser;

	@Column(name = "employee_email")
	private String employeeEmail;

	@Column(name = "sub_total")
	private BigDecimal subTotal;

	@Column(name = "iva_total")
	private BigDecimal ivaTotal;

	@Column(name = "order_total")
	private BigDecimal orderTotal;

	@Column(name = "pending_payment")
	private BigDecimal pendingPayment;

	@Column(name = "discount_total")
	private BigDecimal discountTotal;

	@Column(name = "status")
	private String status;

	@Column(name = "retention_code")
	private String retentionCode;

	@Column(name = "order_type")
	private String orderType;

	@Column(name = "temp_mig_status")
	private String tempMigStatus;

	@Column(name = "is_updated")
	private Boolean isUpdated;

	@Column(name = "observations")
	private String observations;

	@Column(name = "retention_order")
	private Boolean isRetentionOrder;

	@Column(name = "cfdi_type")
	private String cfdiType;

	@Column(name = "time_active")
	private Date timeActive;

	@Column(name = "is_converted")
	private Boolean isConverted;

	@Column(name = "is_invoice_top")
	private Boolean invoiceTop;

	@Column(name = "invoice_top_amount")
	private BigDecimal invoiceAmount;

	@Column(name = "fiscal_addresse", length = 600)
	private String fiscalAddresse;

	@Column(name = "shiping_addresse", length = 600)
	private String shipingAddresse;

	@Column(name = "taxes")
	private String taxes;

}
