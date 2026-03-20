package mx.com.endtoend.infrastructure.userConfiguration.common.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.com.endtoend.infrastructure.orderConfigurations.common.entities.OrderConfigurationEntity;

import java.util.List;

@Entity
@Table(name = "sale_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaleTypeEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "type", nullable = false)
	private String type;

	@Column(name = "module_id", nullable = true)
	private String moduleId;

	@ManyToMany(mappedBy = "saleTypes")
	private List<UserConfigurationEntity> userConfigurations;

	@OneToMany(mappedBy = "saleType")
	private List<OrderConfigurationEntity> orderConfigurations;
}

