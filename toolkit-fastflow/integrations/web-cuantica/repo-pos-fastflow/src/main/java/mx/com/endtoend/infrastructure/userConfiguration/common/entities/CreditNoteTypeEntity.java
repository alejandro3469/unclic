package mx.com.endtoend.infrastructure.userConfiguration.common.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name = "credit_note_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditNoteTypeEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "type", nullable = false)
	private String type;
	
	@ManyToMany(mappedBy = "creditNoteTypes")
	private List<UserConfigurationEntity> userConfigurations;

}
