package mx.com.endtoend.infrastructure.userConfiguration.common.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author ddcasas
 *
 */

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeEntity implements Serializable {

	private static final long serialVersionUID = 3227407189218040301L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", unique = false, nullable = false)
	private Long userId;

    @Column(name = "employee_email", unique = true, nullable = false)
    private String employeeEmail;
    
	@Column(name = "user_number", unique = false, nullable = false)
	private Long userNumber;

	@Column(name = "branch_code", unique = false, nullable = false)
	private String branchCode;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private RoleJobTypeEntity roleJob;

	@JoinColumn(name = "direct_boss")
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private EmployeeEntity directBoss;

	@OneToMany(mappedBy = "directBoss", fetch = FetchType.LAZY)
	private List<EmployeeEntity> employees;

	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
	private UserConfigurationEntity userConfiguration;

}
