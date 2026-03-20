package mx.com.endtoend.infrastructure.openings.common.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "opening_operations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpeningOperationEntity {

	@Id
	@Column(name = "opening_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long openingId;

	@Column(name = "branch_code", nullable = false)
	private String branchCode;

	@Column(name = "employee_email", nullable = false)
	private String employeeEmail;

	@Column(name = "closing_id", nullable = true)
	private Long closingId;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
	private BigDecimal totalAmount;
	
	@Column(name = "close_attempts", nullable = true)
	private int closeAttempts;

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "openingOperation", orphanRemoval = true)
	private List<OpeningOperationDetailEntity> openingOperationDetail;

	@Override
	public String toString() {
		return "OpeningOperationEntity [openingId=" + openingId + ", branchCode=" + branchCode + ", employeeEmail="
				+ employeeEmail + ", closingId=" + closingId + ", isActive=" + isActive + ", totalAmount=" + totalAmount
				+ ", creationDate=" + creationDate + ", openingOperationDetail=" + openingOperationDetail + "]";
	}

}
