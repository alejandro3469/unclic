package mx.com.endtoend.infrastructure.permissions.common.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.com.endtoend.infrastructure.commons.constants.PermissionEnum;
import mx.com.endtoend.infrastructure.roles.common.entities.RoleEntity;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PermissionEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", unique = true, nullable = false)
	@Enumerated(value = EnumType.STRING)
	private PermissionEnum name;

	@Column(name = "module", unique = false, nullable = false)
	private String module;

	@Column(name = "type", unique = false, nullable = true)
	private String type;

	@ManyToMany(mappedBy = "permissions")
	private Collection<RoleEntity> roles;

	public PermissionEntity(Long id, PermissionEnum name, String module, String type) {
		super();
		this.id = id;
		this.name = name;
		this.module = module;
		this.type = type;
	}

}
