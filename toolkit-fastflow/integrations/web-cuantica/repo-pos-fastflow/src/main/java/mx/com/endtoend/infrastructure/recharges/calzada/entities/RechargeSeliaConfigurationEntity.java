package mx.com.endtoend.infrastructure.recharges.calzada.entities;

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
@Table(name = "recharge_configurations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RechargeSeliaConfigurationEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "company_code", nullable = false)
	private String companyCode;

	@Column(name = "originator_code", nullable = false)
	private String originatorCode;

	@Column(name = "user", nullable = false)
	private String user;

	@Column(name = "password", nullable = false)
	private String password;

}
