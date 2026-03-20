package mx.com.endtoend.infrastructure.creditNote.common.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credit_note_details")
@Getter
@Setter
@NoArgsConstructor
public class CreditNoteDetailEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "article_number", unique = false, nullable = false)
	private BigDecimal articleNumber;

	@Column(name = "article_code", unique = false, nullable = false)
	private String articleCode;

	@Column(name = "description_one", unique = false, nullable = false)
	private String descriptionOne;

	@Column(name = "description_two", unique = false, nullable = false)
	private String descriptionTwo;

	@Column(name = "unit_measure", unique = false, nullable = false)
	private String unitMeasure;

	@Column(name = "final_unit_price", unique = false, nullable = false)
	private Double finalUnitPrice;

	@Column(name = "tax_one", unique = false, nullable = false)
	private Double taxOne;

	@Column(name = "tax_two", unique = false, nullable = false)
	private Double taxTwo;

	@Column(name = "request_amount", unique = false, nullable = false)
	private Double requestAmount;

	@Column(name = "is_custom_article", unique = false, nullable = false)
	private boolean isCustomArticle;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private CreditNoteHeaderEntity creditNoteHeader;

	@Override
	public String toString() {
		return "CreditNoteDetailEntity [id=" + id + ", articleNumber=" + articleNumber + ", articleCode=" + articleCode
				+ ", descriptionOne=" + descriptionOne + ", descriptionTwo=" + descriptionTwo + ", unitMeasure="
				+ unitMeasure + ", finalUnitPrice=" + finalUnitPrice + ", taxOne=" + taxOne + ", taxTwo=" + taxTwo
				+ ", requestAmount=" + requestAmount + ", isCustomArticle=" + isCustomArticle + ", creditNoteHeader="
				+ creditNoteHeader + "]";
	}

}
