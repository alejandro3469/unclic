package mx.com.endtoend.infrastructure.advertising.common.entities;

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
@Table(name = "sale_advertising")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleAdvertisingEntity {

	@Id
	@Column(name = "opening_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@Column(name = "order_code", nullable = false)
	private String orderCode;

	@Column(name = "order_number", nullable = false)
	private BigDecimal orderNumber;

	@Column(name = "branch_code", nullable = false)
	private String branchCode;

	@Column(name = "employee_id", nullable = false)
	private Long employeeId;

	@Column(name = "client_number", nullable = false)
	private Long clientNumber;

	@Column(name = "message", nullable = false)
	private String message;

	@Column(name = "price_per_word", nullable = false)
	private BigDecimal pricePerWord;

	@Column(name = "published_day", nullable = false)
	private Long publishedDay;

	@Column(name = "article_number", nullable = false)
	private BigDecimal articleNumber;

	@Column(name = "article_code", nullable = false)
	private String articleCode;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "number_word", nullable = false)
	private Long numberWord;

	@Column(name = "applied_iva", nullable = false)
	private BigDecimal appliedIva;

	@Column(name = "iva", nullable = false)
	private BigDecimal iva;

	@Column(name = "sub_total", nullable = false)
	private BigDecimal subTotal;

	@Column(name = "amount_total", nullable = false)
	private BigDecimal amountTotal;

	@Column(name = "status_code", nullable = false)
	private String statusCode;
}
