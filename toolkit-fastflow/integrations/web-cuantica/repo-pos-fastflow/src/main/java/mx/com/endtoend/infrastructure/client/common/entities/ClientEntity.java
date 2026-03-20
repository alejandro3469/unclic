package mx.com.endtoend.infrastructure.client.common.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.com.endtoend.infrastructure.orders.common.entities.SaleOrderEntity;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "client_number", nullable = true)
	private Long noClient;

	@Column(name = "taxpayer", nullable = true)
	private String taxpayer;

	@Column(name = "customer_type_code", nullable = true)
	private String customerType;

	@Column(name = "rfc", nullable = true)
	private String rfc;

	@Column(name = "name", nullable = true)
	private String name;

	@Column(name = "father_surname", nullable = true)
	private String fatherSurname;

	@Column(name = "mother_surname", nullable = true)
	private String motherSurname;

	@Column(name = "business_name", nullable = true)
	private String businessName;

	@Column(name = "contact", nullable = true)
	private String contact;

	@Column(name = "phone", nullable = true)
	private String phone;

	@Column(name = "cell", nullable = true)
	private String cell;

	@Column(name = "iva", nullable = true)
	private String iva;

	@Column(name = "tax_regime", nullable = true)
	private String taxRegime;

	@Column(name = "howToContact_code", nullable = true)
	private String howToContact;

	@Column(name = "workType_code", nullable = true)
	private String workType;

	private String nnn001;

	@Column(name = "id_company", nullable = true)
	private Long company;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
	private List<SaleOrderEntity> saleOrder;

}