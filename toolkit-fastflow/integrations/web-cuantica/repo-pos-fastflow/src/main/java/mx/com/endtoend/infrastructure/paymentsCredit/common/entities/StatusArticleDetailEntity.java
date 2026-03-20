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
@Table(name = "status_sale_detail_articles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatusArticleDetailEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "status_sale_detail_id", nullable = false)
	private Long statusSaleDetailId;
	
	@Column(name = "detailid", nullable = true)
	private Long detail_id;

	@Column(name = "product_sku", nullable = true)
	private String productsku;

	@Column(name = "cantidad", nullable = true)
	private Double cantidad;

	@Column(name = "total_operacion", nullable = true)
	private Double totalOperacion;

	@Column(name = "tr_disposicion_id", nullable = true)
	private String trDisposicionId;
}
