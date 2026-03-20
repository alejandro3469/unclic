package mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "payment_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentOptionEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "period")
	private String period;

	@Column(name = "commission", precision = 19, scale = 2)
	private BigDecimal commission;

	@Column(name = "is_enable")
	private boolean isEnable;
	
	@ManyToOne(optional = false)
	private CreditCardEntity creditCard;

}
