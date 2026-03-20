package mx.com.endtoend.infrastructure.userConfiguration.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "warehouse_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WarehouseOptionEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "warehouse_code", nullable = false)
	private String warehouseCode;
	
	@Column(name = "warehouse_name", nullable = false)
	private String warehouseName;
	
	@Column(name = "is_default", nullable = false)
	private boolean isDefault;
	
	@Column(name = "is_consumption", nullable = false)
	private boolean isConsumption;
	
	@Column(name = "is_query", nullable = false)
	private boolean isQuery;
	
	@JoinColumn(name = "id_user_configuration", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private UserConfigurationEntity userConfiguration;
}
