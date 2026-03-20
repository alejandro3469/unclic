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
@Table(name = "status_sale_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatusSaleDetailEntity {
	
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "status_sale_response_id", nullable = false)
	private Long statusSaleResponseId;
	
	@Column(name = "tr_id", nullable = true)
	private String trId;

	@Column(name = "total", nullable = true)
	private Double total;

	@Column(name = "status", nullable = true)
	private String status;

	@Column(name = "nota_cancelacion", nullable = true)
	private String notaCancelacion;

	@Column(name = "puntos", nullable = true)
	private Double puntos;

	@Column(name = "status_tex", nullable = true)
	private String statusTex;

	@Column(name = "created_at", nullable = true)
	private String createdAt;

	@Column(name = "updated_at", nullable = true)
	private String updatedAt;

}
