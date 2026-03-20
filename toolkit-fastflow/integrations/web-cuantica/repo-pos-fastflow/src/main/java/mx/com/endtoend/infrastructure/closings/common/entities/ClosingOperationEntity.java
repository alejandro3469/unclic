package mx.com.endtoend.infrastructure.closings.common.entities;

import java.math.BigDecimal;
import java.util.Date;
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

@Entity
@Table(name = "closing_operations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClosingOperationEntity {

	@Id
	@Column(name = "closing_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long closingId;

	@Column(name = "branch_code", nullable = false)
	private String branchCode;

	@Column(name = "employee_email", nullable = false)
	private String employeeEmail;

	@Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
	private BigDecimal totalAmount;

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "closingOperation", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<ClosingOperationDetailEntity> closingOperationDetail;
}
