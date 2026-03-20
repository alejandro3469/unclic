package mx.com.endtoend.infrastructure.creditNote.common.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credit_notes")
@Getter
@Setter
@NoArgsConstructor
public class CreditNoteEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "order_number", unique = false, nullable = false)
	private BigDecimal orderNumber;

	@Column(name = "employee_id", unique = false, nullable = false)
	private Long employeeId;
	
	@Column(name = "order_code", unique = false, nullable = false)
	private String orderCode;

	@Column(name = "order_id", unique = false, nullable = false)
	private Long orderId;

	@Column(name = "order_total", unique = false, nullable = false)
	private Double orderTotal;

	@Column(name = "credit_note_total", unique = false, nullable = false)
	private Double creditNoteTotal;

	@Column(name = "is_total", unique = false, nullable = false)
	private boolean isTotal;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "creditNote", orphanRemoval = true)
	private List<CreditNoteHeaderEntity> creditNoteHeaderList;

	@Override
	public String toString() {
		return "CreditNoteEntity [id=" + id + ", orderNumber=" + orderNumber + ", orderCode=" + orderCode + ", orderId="
				+ orderId + ", orderTotal=" + orderTotal + ", creditNoteTotal=" + creditNoteTotal + ", isTotal="
				+ isTotal + ", creditNoteHeaderList=" + creditNoteHeaderList + "]";
	}

}
