package mx.com.endtoend.infrastructure.company.common.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;

/**
 * 
 * @author ddcasas
 *
 */

@Entity
@Table(name = "methods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MethodEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "method_code", unique = false, nullable = false)
	@Enumerated(value = EnumType.STRING)
	private GenericIdentifyMethods methodCode;

	@Column(name = "module", nullable = false)
	private String module;
	
	@ManyToMany(mappedBy = "methods")
	private List<CompanyEntity> companies;
	
	public MethodEntity(Long id, GenericIdentifyMethods methodCode, String module) {
		super();
		this.id=id;
		this.methodCode=methodCode;
		this.module=module;
	}
}
