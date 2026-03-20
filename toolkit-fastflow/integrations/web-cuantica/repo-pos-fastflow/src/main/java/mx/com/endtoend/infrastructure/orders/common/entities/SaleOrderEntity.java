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
import javax.persistence.JoinColumn;
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
import mx.com.endtoend.infrastructure.client.common.entities.ClientEntity;

@Entity
@Table(name = "sales_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaleOrderEntity {

	@Id
	@Column(name = "order_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@Column(name = "order_code", unique = false, nullable = false)
	private String orderCode;

	@Column(name = "order_type", unique = false, nullable = false)
	private String orderType;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private StatusEntity status;

	@Column(name = "order_number", unique = false, nullable = false)
	private BigDecimal orderNumber;
	
	@Column(name = "company_number", unique = false, nullable = false)
	private String companyNumber;

	@Column(name = "branch_code", nullable = false)
	private String branchCode;

	@Column(name = "currency", unique = false, nullable = false)
	private String currency;

	@Column(name = "exchangeRate", unique = false, nullable = false, precision = 19, scale = 2)
	private BigDecimal exchangeRate;

	@Column(name = "creation_date", unique = false, nullable = false)
	private Date creationDate;

	@Column(name = "request_date", unique = false, nullable = false)
	private Date requestDate;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ClientEntity client;

	@Column(name = "user_number", unique = false, nullable = false)
	private Long userNumber;

	@Column(name = "username_employee", unique = false, nullable = false)
	private String username;

	@Column(name = "client_Tax", unique = false, nullable = false, precision = 19, scale = 2)
	private BigDecimal clientTax;

	@Column(name = "sub_total", unique = false, nullable = false, precision = 19, scale = 2)
	private BigDecimal subTotal;

	@Column(name = "iva_total", unique = false, nullable = false, precision = 19, scale = 2)
	private BigDecimal ivaTotal;

	@Column(name = "order_total", unique = false, nullable = false, precision = 19, scale = 2)
	private BigDecimal orderTotal;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "saleOrder", orphanRemoval = true)
	private List<SaleOrderDetailEntity> saleOrderDetail;

	@OneToOne
	@JoinColumn(name = "fk_order", nullable = false)
	private OrderEntity order;
}
