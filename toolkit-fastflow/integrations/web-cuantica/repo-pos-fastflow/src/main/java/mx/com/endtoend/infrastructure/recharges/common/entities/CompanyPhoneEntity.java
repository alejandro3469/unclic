package mx.com.endtoend.infrastructure.recharges.common.entities;

import java.math.BigDecimal;

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
@Table(name = "companies_phones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompanyPhoneEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "company_phone_name", nullable = false)
	private String companyPhoneName;

	@Column(name = "company_phone_code", nullable = false)
	private Integer companyPhoneCode;

	@Column(name = "company_phoe", nullable = false)
	private Integer companyPhoe;

	@Column(name = "line_type", nullable = false)
	private String lineType;

	@Column(name = "article_code", nullable = false)
	private String articleCode;

	@Column(name = "article_number", nullable = false)
	private BigDecimal articleNumber;

	@Column(name = "storage_type", nullable = false)
	private String storageType;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "amount", nullable = false)
	private Double amount;
}
