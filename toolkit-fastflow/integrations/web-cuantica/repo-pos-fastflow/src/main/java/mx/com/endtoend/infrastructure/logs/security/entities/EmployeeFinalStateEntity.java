package mx.com.endtoend.infrastructure.logs.security.entities;

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

@Entity
@Table(name = "employee_final_states")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFinalStateEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "employee_summary_id", nullable = false)
	private Long employeeSummaryId;

	@Column(name = "user_id", nullable = true)
	private Long userId;

	@Column(name = "user_number", nullable = true)
	private Long userNumber;

	@Column(name = "employee_email", nullable = true)
	private String employeeEmail;

	@Column(name = "branch_code", nullable = true)
	private String branchCode;

	@Column(name = "role_job", nullable = true)
	private String roleJob;

	@Column(name = "direct_boss", nullable = true)
	private String directBoss;

	@Column(name = "employee_list", nullable = true)
	private String employeeList;

}
