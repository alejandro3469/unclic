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
@Table(name = "orders_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetailEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "line_number", unique = false, nullable = false)
	private int lineNumber;
	
	@Column(name = "line_type", unique = false, nullable = true)
	private String lineType;
	
	@Column(name = "storage_type", unique = false, nullable = true)
	private String storageType;
	
	@Column(name = "warehouse_code", unique = false, nullable = true)
	private String warehouseCode;
	
	@Column(name = "article_number", unique = false, nullable = false)
	private BigDecimal articleNumber;
	
	@Column(name = "supplier_number", unique = false)
	private String supplierNumber;
	
	@Column(name = "description_one", unique = false, nullable = false)
	private String descriptionOne;
	
	@Column(name = "description_two", unique = false, nullable = false)
	private String descriptionTwo;
	
	@Column(name = "unit_measurement", unique = false, nullable = false)
	private String unitMeasurement;
	
	@Column(name = "prymary_unit_measurement", unique = false)
	private String primaryUnitMeasure;
	
	@Column(name = "request_amount", unique = false, nullable = false)
	private BigDecimal requestAmount;
	
	@Column(name = "modified_unit_price", unique = false, nullable = false)
	private BigDecimal modifiedUnitPrice;
	
	@Column(name = "unit_price", unique = false, nullable = false)
	private BigDecimal unitPrice;
	
	@Column(name = "fina_unit_price", unique = false, nullable = false)
	private BigDecimal finalUnitPrice;
	
	@Column(name = "sub_total", unique = false, nullable = true)
	private BigDecimal subTotal;
	
	@Column(name = "unit_price_tax", unique = false, nullable = true)
	private BigDecimal unitPriceTax;
	
	@Column(name = "sub_total_tax", unique = false, nullable = true)
	private BigDecimal subTotalTax;
	
	@Column(name = "article_tax", unique = false, nullable = true)
	private BigDecimal articleTax;
	
	@Column(name = "line_code_one", unique = false, nullable = false)
	private String lineCodeOne;

	@Column(name = "line_code_two", unique = false, nullable = false)
	private String lineCodeTwo;
	
	@Column(name = "discount_seller", unique = false, nullable = true)
	private BigDecimal discountSeller;
	
	@Column(name = "final_discount_seller", unique = false, nullable = true)
	private BigDecimal finalDiscountSeller;
	
	@Column(name = "user_number_seller", unique = false, nullable = false)
	private Long uerNumberSeller;
	
	@Column(name = "article_code", unique = false, nullable = false)
	private String articleCode;

	@Column(name = "price_type", unique = false, nullable = false)
	private String priceType;

	@Column(name = "is_retention_article", unique = false)
	private boolean isRetentionArticle;
	
	@Column(name = "retention_code", unique = false, nullable = true)
	private String retentionCode;
	
	@Column(name = "alternate_description", unique = false, nullable = true)
	private String  alternateDescription;
	
	@Column(name = "apply_tax", unique = false, nullable = false)
	private String applyTax;
	
	@Column(name = "tax_value_one", unique = false, nullable = false)
	private BigDecimal taxValueOne;
	
	@Column(name = "tax_value_two", unique = false, nullable = false)
	private BigDecimal taxValueTwo;
	
	@Column(name = "tax_value_three", unique = false, nullable = false)
	private BigDecimal taxValueThree;
	
	@Column(name = "tax_value_four", unique = false, nullable = false)
	private BigDecimal taxValueFour;
	
	@Column(name = "tax_value_five", unique = false, nullable = false)
	private BigDecimal taxValueFive;
	
	@Column(name = "tax_value_by_default", unique = false, nullable = false)
	private BigDecimal taxValueByDefault;
	
	@Column(name = "conversion_factor", unique = false, nullable = false)
	private BigDecimal conversionFactor;

	@Column(name = "is_custum_article", unique = false)
	private boolean isCustumArticle;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private OrderEntity order;
	
}
