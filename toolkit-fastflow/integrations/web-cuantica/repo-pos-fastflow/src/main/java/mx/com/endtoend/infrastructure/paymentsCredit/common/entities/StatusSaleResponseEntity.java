package mx.com.endtoend.infrastructure.paymentsCredit.common.entities;

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
import lombok.ToString;

@Entity
@Table(name = "status_sale_responses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatusSaleResponseEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "order_number", nullable = false)
	private BigDecimal orderNumber;

	@Column(name = "order_code", nullable = false)
	private String orderCode;

	@Column(name = "code", nullable = true)
	private String code;

	@Column(name = "name", nullable = true)
	private String name;

	@Column(name = "type", nullable = true)
	private String type;

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;
	
	@Column(name = "status_active", nullable = true)
	private boolean statusActive;

}
