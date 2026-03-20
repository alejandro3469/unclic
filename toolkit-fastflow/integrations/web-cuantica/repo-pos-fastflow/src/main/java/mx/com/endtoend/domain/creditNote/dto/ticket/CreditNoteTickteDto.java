package mx.com.endtoend.domain.creditNote.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.endtoend.smart.bussiness.model.orders.dto.TaxDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteHeaderDto;
import mx.com.endtoend.domain.users.dto.UserDto;

public class CreditNoteTickteDto {

	private InputStream image;

	private String branchName;

	private String branchCP;

	private String branchStreet;

	private String branchPhone;

	private String branchExternalNumber;

	private String clientNumber;

	private String employeName;

	private String date;

	private List<CreditNoteTickteDetailDto> detail;

	@NotNull(message = "El impuesto 0% es obligatorio")
	@DecimalMin(value = "0.0", message = "El impuesto 0% debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal tax0;

	@NotNull(message = "El impuesto 16% es obligatorio")
	@DecimalMin(value = "0.0", message = "El impuesto 16% debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal tax16;

	@NotNull(message = "El impuesto IEPS es obligatorio")
	@DecimalMin(value = "0.0", message = "El impuesto IEPS debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal taxIEPS;

	@NotNull(message = "El IVA es obligatorio")
	@DecimalMin(value = "0.0", message = "El IVA debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal iva;

	@NotNull(message = "El total de la orden es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de la orden debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal orderTotal;

	private String letterAmount;

	private Integer requestTotal;

	private String creditNote;

	private String folio;

	private String printStatus;

	private String relatedOrder;

	public CreditNoteTickteDto() {
		super();
	}

	public CreditNoteTickteDto(BranchDto branchDto, UserDto userDto, CreditNoteHeaderDto creditNoteHeaderDto,
							   CreditNoteDto creditNote, List<TaxDto> taxList, Double ivaTotal, String letterAmount) {

		this.branchName = branchDto.getName();
		this.branchCP = branchDto.getCp().trim();
		this.branchStreet = branchDto.getStreet();
		this.branchPhone = branchDto.getPhoneNumber().trim();
		this.branchExternalNumber = branchDto.getOutsideNumber().trim();

		this.clientNumber = creditNoteHeaderDto.getClientNumber().toString();
		this.employeName = userDto.getName() + " " + userDto.getFirstSurname() + " " + userDto.getSecondSurname();

		String fomatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(creditNoteHeaderDto.getCreationDate());
		this.date = fomatDate;

		for (TaxDto tax : taxList) {
			if (tax.getTaxValue().equals("0")) {
				this.tax0 = DecimalPrecisionUtils.roundToTwoDecimals(tax.getValue());
			}
			if (tax.getTaxValue().equals("16")) {
				this.tax16 = DecimalPrecisionUtils.roundToTwoDecimals(tax.getValue());
			}
			if (tax.getTaxValue().equals("IEPS")) {
				this.taxIEPS = DecimalPrecisionUtils.roundToTwoDecimals(tax.getValue());
			}
		}

		this.iva = DecimalPrecisionUtils.roundToTwoDecimals(BigDecimal.valueOf(ivaTotal));
		this.orderTotal = DecimalPrecisionUtils.roundToTwoDecimals(creditNoteHeaderDto.getTotalAmount());
		this.letterAmount = letterAmount;

		this.requestTotal = creditNoteHeaderDto.getCreditNoteDetail().size();

		this.creditNote = creditNoteHeaderDto.getCreditNoteCode();
		this.folio = String.valueOf(creditNoteHeaderDto.getFolio().longValue());

		this.relatedOrder = String.valueOf(creditNote.getOrderNumber().longValue()) + "-"
				+ String.valueOf(creditNote.getOrderCode());

		this.printStatus = creditNoteHeaderDto.getIsPrinted() ? "REIMPRESION DE TICKET" : "";
	}

	public InputStream getImage() {
		return image;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getBranchCP() {
		return branchCP;
	}

	public String getBranchStreet() {
		return branchStreet;
	}

	public String getBranchPhone() {
		return branchPhone;
	}

	public String getBranchExternalNumber() {
		return branchExternalNumber;
	}

	public String getClientNumber() {
		return clientNumber;
	}

	public String getEmployeName() {
		return employeName;
	}

	public String getDate() {
		return date;
	}

	public List<CreditNoteTickteDetailDto> getDetail() {
		return detail;
	}

	public BigDecimal getTax0() {
		return tax0;
	}

	public BigDecimal getTax16() {
		return tax16;
	}

	public BigDecimal getTaxIEPS() {
		return taxIEPS;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public String getLetterAmount() {
		return letterAmount;
	}

	public Integer getRequestTotal() {
		return requestTotal;
	}

	public String getCreditNote() {
		return creditNote;
	}

	public String getFolio() {
		return folio;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public void setBranchCP(String branchCP) {
		this.branchCP = branchCP;
	}

	public void setBranchStreet(String branchStreet) {
		this.branchStreet = branchStreet;
	}

	public void setBranchPhone(String branchPhone) {
		this.branchPhone = branchPhone;
	}

	public void setBranchExternalNumber(String branchExternalNumber) {
		this.branchExternalNumber = branchExternalNumber;
	}

	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}

	public void setEmployeName(String employeName) {
		this.employeName = employeName;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setDetail(List<CreditNoteTickteDetailDto> detail) {
		this.detail = detail;
	}

	public void setTax0(BigDecimal tax0) {
		this.tax0 = DecimalPrecisionUtils.roundToTwoDecimals(tax0);
	}

	public void setTax16(BigDecimal tax16) {
		this.tax16 = DecimalPrecisionUtils.roundToTwoDecimals(tax16);
	}

	public void setTaxIEPS(BigDecimal taxIEPS) {
		this.taxIEPS = DecimalPrecisionUtils.roundToTwoDecimals(taxIEPS);
	}

	public void setIva(BigDecimal iva) {
		this.iva = DecimalPrecisionUtils.roundToTwoDecimals(iva);
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = DecimalPrecisionUtils.roundToTwoDecimals(orderTotal);
	}

	public void setLetterAmount(String letterAmount) {
		this.letterAmount = letterAmount;
	}

	public void setRequestTotal(Integer requestTotal) {
		this.requestTotal = requestTotal;
	}

	public void setCreditNote(String creditNote) {
		this.creditNote = creditNote;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public String getRelatedOrder() {
		return relatedOrder;
	}

	public void setRelatedOrder(String relatedOrder) {
		this.relatedOrder = relatedOrder;
	}

	@Override
	public String toString() {
		return "CreditNoteTickteDto [image=" + image + ", branchName=" + branchName + ", branchCP=" + branchCP
				+ ", branchStreet=" + branchStreet + ", branchPhone=" + branchPhone + ", branchExternalNumber="
				+ branchExternalNumber + ", clientNumber=" + clientNumber + ", employeName=" + employeName + ", date="
				+ date + ", detail=" + detail + ", tax0=" + tax0 + ", tax16=" + tax16 + ", taxIEPS=" + taxIEPS
				+ ", iva=" + iva + ", orderTotal=" + orderTotal + ", letterAmount=" + letterAmount + ", requestTotal="
				+ requestTotal + ", creditNote=" + creditNote + ", folio=" + folio + ", printStatus=" + printStatus
				+ ", relatedOrder=" + relatedOrder + "]";
	}

}
