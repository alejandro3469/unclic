package mx.com.endtoend.infrastructure.creditNote.common.entities;

import java.math.BigDecimal;
import java.util.Date;
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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credit_note_headers")
@Getter
@Setter
@NoArgsConstructor
public class CreditNoteHeaderEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "folio", unique = false, nullable = false)
	private BigDecimal folio;

	@Column(name = "credit_note_code", unique = false, nullable = false)
	private String creditNoteCode;

	@Column(name = "total_amount", unique = false, nullable = false)
	private BigDecimal totalAmount;

	@Column(name = "used_amount", unique = false, nullable = false)
	private BigDecimal usedAmount;

	@Column(name = "pending_amount", unique = false, nullable = false)
	private BigDecimal pendingAmount;

	@Column(name = "creation_date", unique = false, nullable = false)
	private Date creationDate;

	@Column(name = "client_id", unique = false, nullable = false)
	private Long clientId;

	@Column(name = "sale_employee_id", unique = false, nullable = false)
	private Long saleEmployeeId;

	@Column(name = "client_number", unique = false, nullable = false)
	private Long clientNumber;

	@Column(name = "currency", unique = false, nullable = true)
	private String currency;

	@Column(name = "exchange_rate", unique = false, nullable = true)
	private BigDecimal exchangeRate;

	@Column(name = "is_printed", nullable = false)
	private boolean isPrinted;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private CreditNoteEntity creditNote;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "creditNoteHeader", orphanRemoval = true)
	private List<CreditNoteDetailEntity> creditNoteDetail;

	@Override
	public String toString() {
		return "CreditNoteHeaderEntity [id=" + id + ", folio=" + folio + ", creditNoteCode=" + creditNoteCode
				+ ", totalAmount=" + totalAmount + ", usedAmount=" + usedAmount + ", pendingAmount=" + pendingAmount
				+ ", creationDate=" + creationDate + ", clientId=" + clientId + ", clientNumber=" + clientNumber
				+ ", creditNote=" + creditNote + ", creditNoteDetail=" + creditNoteDetail + "]";
	}

}
