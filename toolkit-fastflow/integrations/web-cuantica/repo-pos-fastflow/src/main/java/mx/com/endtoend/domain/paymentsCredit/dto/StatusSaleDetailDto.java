package mx.com.endtoend.domain.paymentsCredit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class StatusSaleDetailDto {

	private String tr_id;

	@NotNull(message = "El total es obligatorio")
	@DecimalMin(value = "0.0", message = "El total debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal total;

	private String status;

	private String nota_cancelacion;

	@NotNull(message = "Los puntos son obligatorios")
	@DecimalMin(value = "0.0", message = "Los puntos deben ser mayores o iguales a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal puntos;

	private List<StatusArticleDetail> detail;

	private String status_text;

	private String created_at;

	private String updated_at;

	public StatusSaleDetailDto() {
		super();
	}

	public StatusSaleDetailDto(String tr_id, BigDecimal total, String status, String nota_cancelacion, BigDecimal puntos,
			List<StatusArticleDetail> detail, String status_tex, String created_at, String updated_at) {
		super();
		this.tr_id = tr_id;
		this.total = DecimalPrecisionUtils.roundToTwoDecimals(total);
		this.status = status;
		this.nota_cancelacion = nota_cancelacion;
		this.puntos = DecimalPrecisionUtils.roundToTwoDecimals(puntos);
		this.detail = detail;
		this.status_text = status_tex;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public String getTr_id() {
		return tr_id;
	}

	public void setTr_id(String tr_id) {
		this.tr_id = tr_id;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = DecimalPrecisionUtils.roundToTwoDecimals(total);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNota_cancelacion() {
		return nota_cancelacion;
	}

	public void setNota_cancelacion(String nota_cancelacion) {
		this.nota_cancelacion = nota_cancelacion;
	}

	public BigDecimal getPuntos() {
		return puntos;
	}

	public void setPuntos(BigDecimal puntos) {
		this.puntos = DecimalPrecisionUtils.roundToTwoDecimals(puntos);
	}

	public List<StatusArticleDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<StatusArticleDetail> detail) {
		this.detail = detail;
	}

	public String getStatus_text() {
		return status_text;
	}

	public void setStatus_text(String status_text) {
		this.status_text = status_text;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	@Override
	public String toString() {
		return "StatusSaleDetailDto [tr_id=" + tr_id + ", total=" + total + ", status=" + status + ", nota_cancelacion="
				+ nota_cancelacion + ", puntos=" + puntos + ", detail=" + detail + ", status_tex=" + status_text
				+ ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
	}

}
