package mx.com.endtoend.infrastructure.company.common.entities;

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
@Table(name = "companies_permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PermissionCompanyEntity {
	
	 @Id
	 @Column(name = "id", unique = true, nullable = false)
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @Column(name = "company_id", unique = false, nullable = false)
	 private Long companyId;
	 
	 @Column(name = "permission_id", unique = false, nullable = false)
	 private Long permissionId;

}
