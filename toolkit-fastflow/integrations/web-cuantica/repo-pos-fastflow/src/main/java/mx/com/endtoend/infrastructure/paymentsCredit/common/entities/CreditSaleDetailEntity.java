package mx.com.endtoend.infrastructure.paymentsCredit.common.entities;

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
@Table(name = "credit_sale_request_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditSaleDetailEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "credit_sale_request_id", nullable = false)
	private Long creditSaleRequestId;

	@Column(name = "product_sku", nullable = true)
	private String productSku;

	@Column(name = "amount", nullable = true)
	private Double amount;

	@Column(name = "total_operacion", nullable = true)
	private Double totalOperacion;

}
