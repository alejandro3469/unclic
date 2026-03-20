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
@Table(name = "permissions_final_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionFinalStateEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "role_final_id")
	private Long roleFinalId;

	@Column(name = "name", unique = false, nullable = false)
	private String name;

	@Column(name = "module", unique = false, nullable = false)
	private String module;

	@Column(name = "type", unique = false, nullable = true)
	private String type;
}
