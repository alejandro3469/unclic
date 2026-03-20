package mx.com.endtoend.infrastructure.userConfiguration.common.entities;

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
import lombok.ToString;

@Entity
@Table(name = "role_job_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleJobTypeEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code", unique = false, nullable = false)
	private String code;

	@Column(name = "name", unique = false, nullable = false)
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "roleJob")
	private List<EmployeeEntity> employees;

}
