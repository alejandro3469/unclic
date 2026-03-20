package mx.com.endtoend.infrastructure.catalogue.article.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "article_families")
public class ArticleFamilyEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "enable")
	private boolean enable;

	@Column(name = "code", unique = true, nullable = false)
	private String code;

	@Column(name = "value", unique = true, nullable = false)
	private String value;
}
