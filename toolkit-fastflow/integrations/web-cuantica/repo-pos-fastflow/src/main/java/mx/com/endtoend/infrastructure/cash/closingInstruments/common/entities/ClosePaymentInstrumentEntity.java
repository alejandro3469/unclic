package mx.com.endtoend.infrastructure.cash.closingInstruments.common.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.com.endtoend.infrastructure.closings.common.entities.ClosingOperationDetailEntity;

@Entity
@Table(name = "closing_payment_instruments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClosePaymentInstrumentEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "income_type")
	private String incomeType;

	@Column(name = "is_enabled")
	private boolean isEnabled;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "closePaymentInstrument", fetch = FetchType.EAGER)
	private List<ClosingOperationDetailEntity> closingOperationDetails;
}
