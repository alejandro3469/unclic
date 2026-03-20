package mx.com.endtoend.infrastructure.branch.common.entities;

import java.util.List;

import javax.persistence.*;

import lombok.*;
import mx.com.endtoend.infrastructure.company.common.entities.CompanyEntity;
import mx.com.endtoend.infrastructure.users.common.entities.UserEntity;

@Entity
@Table(name = "branches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BranchEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "street", nullable = true)
	private String street;
	
	@Column(name = "inside_number", nullable = true)
	private String insideNumber;
	
	@Column(name = "outside_number", nullable = true)
	private String outsideNumber;
	
	@Column(name = "cp", nullable = true)
	private String cp;
	
	@Column(name = "colony", nullable = true)
	private String colony;
	
	@Column(name = "phone_number", nullable = true)
	private String phoneNumber;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private CompanyEntity company;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
	private List<UserEntity> users;
	
}
