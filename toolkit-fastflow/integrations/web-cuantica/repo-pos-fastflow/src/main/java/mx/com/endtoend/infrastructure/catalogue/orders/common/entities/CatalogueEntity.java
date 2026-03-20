package mx.com.endtoend.infrastructure.catalogue.orders.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "catalog")
public class CatalogueEntity {

	 @Id
	 @Column(name = "id", unique = true, nullable = false)
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @Column(name = "code")
	 private String code;
	 
	 @Column(name = "name")
	 private String name;
	 
	 @Column(name = "catalog_type", nullable = false)
	 private String type;

	
	 public String getName() {
	 		return this.name.trim();
	 	}
}
