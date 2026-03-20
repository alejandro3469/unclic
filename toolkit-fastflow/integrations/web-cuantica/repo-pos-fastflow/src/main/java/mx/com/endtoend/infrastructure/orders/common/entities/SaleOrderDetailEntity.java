package mx.com.endtoend.infrastructure.orders.common.entities;

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
import lombok.ToString;

@Entity
@Table(name = "sale_orders_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaleOrderDetailEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "line_number", unique = false, nullable = false)
	private int lineNumber;

	@Column(name = "line_type", unique = false, nullable = true)
	private String lineType;

	@Column(name = "warehouse_code", unique = false, nullable = true)
	private String warehouseCode;

	@Column(name = "article_number", unique = false, nullable = false)
	private BigDecimal articleNumber;

	@Column(name = "article_description", unique = false, nullable = false)
	private String articleDescription;

	@Column(name = "unit_measurement", unique = false, nullable = false)
	private String unitMeasurement;

	@Column(name = "request_amount", unique = false, nullable = false, precision = 19, scale = 2)
	private BigDecimal requestAmount;

	@Column(name = "article_price", unique = false, nullable = false, precision = 19, scale = 2)
	private BigDecimal articlePrice;

	@Column(name = "sub_total", unique = false, nullable = true, precision = 19, scale = 2)
	private BigDecimal subTotal;

	@Column(name = "currency", unique = false, nullable = true)
	private String currency;

	@Column(name = "exchange_rate", unique = false, nullable = true, precision = 19, scale = 2)
	private BigDecimal exchangeRate;

	@Column(name = "line_code_one", unique = false, nullable = false)
	private String lineCodeOne;

	@Column(name = "line_code_two", unique = false, nullable = false)
	private String lineCodeTwo;

	@Column(name = "weight", unique = false, nullable = true, precision = 19, scale = 2)
	private BigDecimal weight;

	@Column(name = "weight_factor", unique = false, nullable = true, precision = 19, scale = 2)
	private BigDecimal weightFactor;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private SaleOrderEntity saleOrder;
}
