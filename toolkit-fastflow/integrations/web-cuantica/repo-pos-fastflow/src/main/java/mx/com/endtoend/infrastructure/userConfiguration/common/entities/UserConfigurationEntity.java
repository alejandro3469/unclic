package mx.com.endtoend.infrastructure.userConfiguration.common.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "user_configurations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserConfigurationEntity implements Serializable{

	private static final long serialVersionUID = 7417911498251887710L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "percentage_authorized", unique = false, nullable = false)
	private BigDecimal percentageAuthorized;

	@Column(name = "authorization_code", unique = false, nullable = false)
	private String authorizationCode;

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@Column(name = "updated_date", nullable = false)
	private Date updatedDate;

	@Column(name = "modify_by", nullable = false)
	private String modifyBy;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="user_configurations_sale_types",
			joinColumns = @JoinColumn(
					name = "user_configuration_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "sale_type_id", referencedColumnName = "id"))
	private List<SaleTypeEntity> saleTypes;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="user_configurations_credit_note_types",
			joinColumns = @JoinColumn(
					name = "user_configuration_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "credit_note_type_id", referencedColumnName = "id"))
	private List<CreditNoteTypeEntity> creditNoteTypes;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="user_configurations_price_types",
			joinColumns = @JoinColumn(
					name = "user_configuration_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "pice_type_id", referencedColumnName = "id"))
	private List<PriceTypeEntity> priceTypes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userConfiguration", orphanRemoval = true)
	private List<WarehouseOptionEntity> warehouses;

	@OneToOne
	@JoinColumn(name = "fk_employee_id", nullable = false)
	private EmployeeEntity employee;

}
