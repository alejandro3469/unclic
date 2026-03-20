package mx.com.endtoend.infrastructure.roles.common.entities;

import java.util.Collection;

import javax.persistence.*;
import lombok.*;
import mx.com.endtoend.infrastructure.permissions.common.entities.PermissionEntity;
import mx.com.endtoend.infrastructure.users.common.entities.UserEntity;
/**
 * 
 * @author ddcasas
 *
 */

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="company_key")
	private String companyKey;
	
	@Column(name = "enabled", nullable = false, updatable = true)
	private boolean enabled = true;
	
	@ManyToMany(mappedBy = "roles")
	private Collection<UserEntity> users;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "roles_permissions",
			joinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "permission_id", referencedColumnName = "id"))
	private Collection<PermissionEntity> permissions;
	
	public RoleEntity(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
}
