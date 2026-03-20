package mx.com.endtoend.infrastructure.company.common.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.com.endtoend.infrastructure.branch.common.entities.BranchEntity;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompanyEntity {
	 
	 @Id
	 @Column(name = "id", unique = true, nullable = false)
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @Column(name = "name", nullable = false)
	 private String name;
	 
	 @Column(name = "code", unique = false, nullable = false)
	 @Enumerated(value = EnumType.STRING)
	 private CompanyCodes code;
	 
	 @Column(name = "company_number", unique = false, nullable = true)
	 private String companyNumber;
	 
	 @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
	 private List<BranchEntity> branches;
	 
	 @Column(name = "apply_invoice_process", unique = false, nullable = true)
	 private boolean applyInvoiceProcess;
	 
	 @Column(name = "rfc", unique = false, nullable = true)
	 private String rfc;
	 
	 @ManyToMany(fetch = FetchType.LAZY)
	 @JoinTable(
			 name="companies_methods",
			 joinColumns = @JoinColumn(
					 name = "company_id", referencedColumnName = "id"),
			 inverseJoinColumns = @JoinColumn(
					 name = "method_id",referencedColumnName = "id"))
	 private List<MethodEntity> methods;
	 
}
