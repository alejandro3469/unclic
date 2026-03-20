package mx.com.endtoend.infrastructure.logs.orders.entities;

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

@Entity
@Table(name = "order_detail_prev_state_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailPreviousStateEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "order_prev_state_id")
	private Long orderPrevStateId;
	
	@Column(name = "line_number")
	private int lineNumber;
	
	@Column(name = "line_type")
	private String lineType;
	
	@Column(name = "storage_type")
	private String storageType;
	
	@Column(name = "warehouse_code")
	private String warehouseCode;
	
	@Column(name = "article_number")
	private BigDecimal articleNumber;
	
	@Column(name = "supplier_number")
	private String supplierNumber;
	
	@Column(name = "description_one")
	private String descriptionOne;
	
	@Column(name = "description_two")
	private String descriptionTwo;
	
	@Column(name = "unit_measurement")
	private String unitMeasurement;
	
	@Column(name = "prymary_unit_measurement")
	private String primaryUnitMeasure;
	
	@Column(name = "request_amount")
	private BigDecimal requestAmount;
	
	@Column(name = "modified_unit_price")
	private BigDecimal modifiedUnitPrice;
	
	@Column(name = "unit_price")
	private BigDecimal unitPrice;
	
	@Column(name = "fina_unit_price")
	private BigDecimal finalUnitPrice;
	
	@Column(name = "sub_total")
	private BigDecimal subTotal;
	
	@Column(name = "unit_price_tax")
	private BigDecimal unitPriceTax;
	
	@Column(name = "sub_total_tax")
	private BigDecimal subTotalTax;
	
	@Column(name = "article_tax")
	private BigDecimal articleTax;
	
	@Column(name = "line_code_one")
	private String lineCodeOne;

	@Column(name = "line_code_two")
	private String lineCodeTwo;
	
	@Column(name = "discount_seller")
	private BigDecimal discountSeller;
	
	@Column(name = "user_number_seller")
	private Long uerNumberSeller;
	
	@Column(name = "article_code")
	private String articleCode;

	@Column(name = "price_type")
	private String priceType;

	@Column(name = "is_retention_article")
	private Boolean isRetentionArticle;
	
	@Column(name = "retention_code")
	private String retentionCode;
	
	@Column(name = "alternate_description")
	private String  alternateDescription;
	
	@Column(name = "apply_tax")
	private String applyTax;
	
	@Column(name = "tax_value_one")
	private BigDecimal taxValueOne;
	
	@Column(name = "tax_value_two")
	private BigDecimal taxValueTwo;
	
	@Column(name = "tax_value_three")
	private BigDecimal taxValueThree;
	
	@Column(name = "tax_value_four")
	private BigDecimal taxValueFour;
	
	@Column(name = "tax_value_five")
	private BigDecimal taxValueFive;
	
	@Column(name = "tax_value_by_default")
	private BigDecimal taxValueByDefault;
	
	@Column(name = "conversion_factor")
	private BigDecimal conversionFactor;

	@Column(name = "is_custum_article")
	private Boolean isCustumArticle;

}
