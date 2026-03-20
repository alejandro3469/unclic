package mx.com.endtoend.infrastructure.cash.bankReference.common.entities;

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
@Table(name = "bank_referens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "banking_institution", unique = true, nullable = false)
	private String bankingInstitution;

	@Column(name = "use_type", nullable = false)
	private String useType;

	@Column(name = "is_enabled")
	private boolean isEnable;

}
