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
@Table(name = "catalog_direction")
public class CatalogDirectionEntity {

	@Id
	 @Column(name = "id", unique = true, nullable = false)
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @Column(name = "postal_code", nullable = false)
	 private String cp;
	 
	 @Column(name = "state_id")
	 private String state;
	 
	 @Column(name = "city", nullable = false)
	 private String city;
	 
	 @Column(name = "delegation", nullable = false)
	 private String delegation;
	 
	 @Column(name = "country_id")
	 private String country;
	 
	 @Column(name = "colony", nullable = false)
	 private String colony;

	 public String getCp() {
		 	return this.cp.trim();
	 }
		
	 public String getState() {
		 return this.state.trim();
	 }
		
	 public String getCity() {
		 return this.city.trim();
	 }
	 public String getDelegation() {
		 	return this.delegation.trim();
	 }
		
	 public String getCountry() {
		 return this.country.trim();
	 }
		
	 public String getColony() {
		 return this.colony.trim();
	 }
}
