package mx.com.endtoend.infrastructure.accountingRecord.common.entities;

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
@Table(name = "accounting_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountingRecordEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "date", unique = false, nullable = false)
	private Date date;

	@Column(name = "employee_id", unique = false, nullable = false)	
	private Long employeeId;

	@Column(name = "branch_code", unique = false, nullable = false)
	private String branchCode;

	@Column(name = "opening_id", unique = false, nullable = false)
	private Long openingId;

	@Column(name = "transaction_id", unique = false, nullable = false)
	private Long transactionId;

	@Column(name = "accounting_concept", unique = false, nullable = false)
	private String accountingConcept;

	@Column(name = "movement_type", unique = false, nullable = false)
	private String movementType;

	@Column(name = "amount_applied", nullable = false, precision = 19, scale = 2)
    private BigDecimal amountApplied;
	
	@Column(name = "movement_concept", unique = false, nullable = false)
	private String movementConcept;

}
