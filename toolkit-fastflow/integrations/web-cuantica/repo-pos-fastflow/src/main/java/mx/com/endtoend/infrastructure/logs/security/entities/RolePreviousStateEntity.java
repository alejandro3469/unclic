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
@Table(name = "roles_previous_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolePreviousStateEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "role_summary_id")
	private Long roleSummaryId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="company_key")
	private String companyKey;
	
	@Column(name = "enabled", nullable = false, updatable = true)
	private boolean enabled = true;

}
