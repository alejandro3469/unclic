package mx.com.endtoend.infrastructure.logs.security.entities;

import java.math.BigDecimal;
import java.util.Date;

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
@Table(name = "user_config_prev_states")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserConfigurationPrevStateEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "employee_prev_id", nullable = false)
	private Long employeePrevId;

	@Column(name = "percentage_authorized", nullable = true)
	private BigDecimal percentageAuthorized;

	@Column(name = "authorization_code", nullable = true)
	private String authorizationCode;

	@Column(name = "creation_date", nullable = true)
	private Date creationDate;

	@Column(name = "updated_date", nullable = true)
	private Date updatedDate;

	@Column(name = "modifyBy", nullable = true)
	private String modifyBy;

	@Column(name = "sale_types", nullable = true)
	private String saleTypes;

	@Column(name = "credit_note_types", nullable = true)
	private String creditNoteTypes;

	@Column(name = "price_types", nullable = true)
	private String priceTypes;

	@Column(name = "warehouse_options", nullable = true)
	private String warehouseOptions;
}
