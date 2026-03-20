package mx.com.endtoend.infrastructure.articles.common.entities;

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
@Table(name = "custom_articles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomArticleEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@Column(name = "price", unique = false, nullable = true, precision = 19, scale = 2)
	private BigDecimal price;
	
	@Column(name = "enable")
	private boolean enable;
	
	@Column(name = "article_number")
	private BigDecimal articleNumber;
	
	@Column(name = "sale_type")
	private String saleType;
	
}
