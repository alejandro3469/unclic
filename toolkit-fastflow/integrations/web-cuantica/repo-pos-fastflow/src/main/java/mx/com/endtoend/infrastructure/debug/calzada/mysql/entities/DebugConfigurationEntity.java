package mx.com.endtoend.infrastructure.debug.calzada.mysql.entities;

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
@Table(name = "debug_configurations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DebugConfigurationEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	@Column(name = "module")
	private String module;
	
	@Column(name = "active")
	private boolean active;
}
