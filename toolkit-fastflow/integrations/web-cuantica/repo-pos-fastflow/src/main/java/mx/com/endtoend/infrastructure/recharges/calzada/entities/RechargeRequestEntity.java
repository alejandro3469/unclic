package mx.com.endtoend.infrastructure.recharges.calzada.entities;

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
import lombok.ToString;

@Entity
@Table(name = "recharge_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RechargeRequestEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "order_number", nullable = false)
	private BigDecimal orderNumber;

	@Column(name = "order_code", nullable = false)
	private String orderCode;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@Column(name = "company_phone", nullable = false)
	private String companyPhone;

	@Column(name = "amount", nullable = false)
	private Double amount;

	@Column(name = "auth_code", nullable = false)
	private String authCode;

	@Column(name = "reference", nullable = false)
	private String reference;

}
