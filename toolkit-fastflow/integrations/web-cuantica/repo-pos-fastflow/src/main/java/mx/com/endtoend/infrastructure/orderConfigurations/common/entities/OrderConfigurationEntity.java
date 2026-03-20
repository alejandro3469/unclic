package mx.com.endtoend.infrastructure.orderConfigurations.common.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.SaleTypeEntity;

@Entity
@Table(name = "order_configurations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderConfigurationEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private SaleTypeEntity saleType;

	@Column(name = "line_code_one")
	private String lineCodeOne;

	@Column(name = "line_code_two")
	private String lineCodeTwo;

	@Column(name = "retention_code")
	private String retentionCode;

	@Column(name = "apply_conversion")
	private boolean isApplyConversion;

	@Column(name = "apply_top_invoice")
	private boolean isApplyTopInvoice;

	@Column(name = "apply_credit_not", nullable = true)
	private boolean isApplyCreditNote;

	@Column(name = "credit_note_code", nullable = true)
	private String creditNoteCode;

	@Column(name = "state_one_credit_note", nullable = true)
	private String stateOneCreditNote;

	@Column(name = "state_two_credit_note", nullable = true)
	private String stateTwoCreditNote;

	@Column(name = "state_one_valid_credit_note", nullable = true)
	private String stateOneValidCreditNote;

	@Column(name = "state_two_valid_credit_note", nullable = true)
	private String stateTwoValidCreditNote;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "orderConfiguration", orphanRemoval = true)
	private List<DocumentEntity> documents;

}

