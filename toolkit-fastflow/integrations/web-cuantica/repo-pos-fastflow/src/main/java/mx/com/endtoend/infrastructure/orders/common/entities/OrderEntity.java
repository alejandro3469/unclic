package mx.com.endtoend.infrastructure.orders.common.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.StatusEntity;


@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	@Column(name = "order_number", unique = false, nullable = false)
	private BigDecimal orderNumber;
	
	@Column(name = "batch_folio", unique = false, nullable = true)
	private Long batchFolio;
	
	@Column(name = "order_code", unique = false, nullable = false)
	private String orderCode;
		
	@Column(name = "branch_code", nullable = false)
	private String branchCode;
	
	@Column(name = "company_number", nullable = true)
	private String companyNumber;
	
	@Column(name = "currency", unique = false, nullable = false)
	private String currency;
	
	@Column(name = "exchangeRate", unique = false, nullable = false, precision = 19, scale = 2)
	private BigDecimal exchangeRate;
	
	@Column(name = "creating_date", unique = false, nullable = false)
	private Date creationDate;
	
	@Column(name = "request_date", unique = false, nullable = false)
	private Date requestDate;
	
	@Column(name = "validity_date", unique = false, nullable = false)
	private Date validityDate;
	
    @Column(name = "client_id", unique = false, nullable = false)
    private long clientId;
	
	@Column(name = "client_Tax", unique = false, nullable = false, precision = 19, scale = 2)
	private BigDecimal clientTax;
	
	@Column(name = "client_reference", unique = false, nullable = true)
	private String clientReference;
	
	@Column(name = "user_number", unique = false, nullable = false)
	private Long userNumber;
	
	@Column(name = "id_user", unique = false, nullable = false)
	private Long idUser;
	
	@Column(name = "employee_email", unique = false, nullable = false)
	private String employeeEmail;
	
	@Column(name = "sub_total", unique = false, nullable = false, precision = 19, scale = 2)
	private BigDecimal subTotal;
	
	@Column(name = "iva_total", unique = false, nullable = false, precision = 19, scale = 2)
	private BigDecimal ivaTotal;
	
	@Column(name = "order_total", unique = false, nullable = false, precision = 19, scale = 2)
	private BigDecimal orderTotal;
	
	@Column(name = "pending_payment", unique = false, nullable = false, precision = 19, scale = 2)
	private BigDecimal pendingPayment;
	
	@Column(name = "discount_total", unique = false, nullable = false, precision = 19, scale = 2)
	private BigDecimal discountTotal;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private StatusEntity status;

	@Column(name = "retention_code", unique = false, nullable = true)
	private String retentionCode;
	
	@Column(name = "order_type", unique = false, nullable = false)
	private String orderType;
	
	@Column(name = "temp_mig_status")
	private String tempMigStatus;
	
	@Column(name = "is_updated", nullable = false)
	private boolean isUpdated;
	
	@Column(name = "observations", unique = false, nullable = true)
	private String observations;
	
	@Column(name = "retention_order", nullable = false)
	private boolean isRetentionOrder;
	
	@Column(name = "cfdi_type", unique = false, nullable = false)
	private String cfdiType;
	
	@Column(name = "time_active")
	private Date timeActive;
	
	@Column(name = "is_converted")
	private boolean isConverted;
	
	@Column(name = "is_invoice_top")
	private boolean invoiceTop;
	
	@Column(name = "invoice_top_amount", precision = 19, scale = 2)
	private BigDecimal invoiceAmount;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order",orphanRemoval = true)
	private List<AddressEntity> addresses;
		
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order",orphanRemoval = true)
	private List<TaxEntity> taxes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order",orphanRemoval = true)
	private List<OrderDetailEntity> orderDetail;
	
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
	private SaleOrderEntity saleOrder;
	

}
