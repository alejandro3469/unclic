package mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "credit_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditCardEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "banking_institution")
	private String bankingInstitution;

	@Column(name = "code")
	private String code;

	@Column(name = "type")
	private String type;
	
	@Column(name = "is_enable")
	private boolean isEnable;
	
	@Column(name = "minimum_amount", nullable = true, precision = 19, scale = 2)
	private BigDecimal minimumAmount;

	@Column(name = "is_commission_apply", nullable = true)
	private Boolean isCommissionApply;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "creditCard",orphanRemoval = true, fetch = FetchType.EAGER)
	private List<PaymentOptionEntity> paymentOptionDetail;

}
