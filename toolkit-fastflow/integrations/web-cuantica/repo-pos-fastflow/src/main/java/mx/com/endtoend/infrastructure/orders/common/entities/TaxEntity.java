package mx.com.endtoend.infrastructure.orders.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orders_taxes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaxEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "tax_value", unique = false, nullable = false)
	private String taxValue;
	
	@Column(name = "value", unique = false, nullable = false)
	private double value;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private OrderEntity order;
}
