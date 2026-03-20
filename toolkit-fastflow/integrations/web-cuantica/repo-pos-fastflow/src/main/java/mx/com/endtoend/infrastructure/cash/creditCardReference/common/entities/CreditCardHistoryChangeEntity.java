package mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities;

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
@Table(name = "credit_card_history_changes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditCardHistoryChangeEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "credit_card_id", nullable = false)
	private Long creditCardId;

	@Column(name = "banking_institution", nullable = false)
	private String bankingInstitution;

	@Column(name = "type", nullable = false)
	private String type;

	@Column(name = "period", nullable = false)
	private String period;

	@Column(name = "commission", nullable = false)
	private Double commission;

	@Column(name = "is_enable", nullable = false)
	private boolean isEnable;

	@Column(name = "updated_date", nullable = false)
	private Date updatedDate;

	@Column(name = "modified_by", nullable = false)
	private String modifiedBy;
}
