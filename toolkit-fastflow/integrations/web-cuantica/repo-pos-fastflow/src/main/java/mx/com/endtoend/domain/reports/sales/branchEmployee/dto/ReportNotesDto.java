package mx.com.endtoend.domain.reports.sales.branchEmployee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ReportNotesDto {
    private String ordenCreacion;
    private String notaCreacion;
    private String notaTipo;
    private BigDecimal notaFolio;
    
    @NotNull(message = "El total de la nota es obligatorio")
    @DecimalMin(value = "0.0", message = "El total de la nota debe ser mayor o igual a 0")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal notaTotal;
    
    @NotNull(message = "El monto pendiente es obligatorio")
    @DecimalMin(value = "0.0", message = "El monto pendiente debe ser mayor o igual a 0")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal notaMontoPendiente;
    
    @NotNull(message = "El monto usado es obligatorio")
    @DecimalMin(value = "0.0", message = "El monto usado debe ser mayor o igual a 0")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal notaMontoUsado;
    
    private String sucursal;
    private String ordenCodigo;
    private BigDecimal ordenNumero;
    private Long clienteId;
    private String clienteNombre;

    // Constructor que coincide con los parámetros de la consulta
    public ReportNotesDto(String ordenCreacion, String notaCreacion, String notaTipo, BigDecimal notaFolio, Double notaTotal,
                          Double notaMontoPendiente, Double notaMontoUsado, String sucursal, String ordenCodigo, BigDecimal ordenNumero,
                          Long clienteId, String clienteNombre) {
        this.ordenCreacion = ordenCreacion;
        this.notaCreacion = notaCreacion;
        this.notaTipo = notaTipo;
        this.notaFolio = notaFolio;
        this.notaTotal = DecimalPrecisionUtils.roundToTwoDecimals(BigDecimal.valueOf(notaTotal));
        this.notaMontoPendiente = DecimalPrecisionUtils.roundToTwoDecimals(BigDecimal.valueOf(notaMontoPendiente));
        this.notaMontoUsado = DecimalPrecisionUtils.roundToTwoDecimals(BigDecimal.valueOf(notaMontoUsado));
        this.sucursal = sucursal;
        this.ordenCodigo = ordenCodigo;
        this.ordenNumero = ordenNumero;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
    }

    public String getOrdenCreacion() {
        return ordenCreacion;
    }

    public void setOrdenCreacion(String ordenCreacion) {
        this.ordenCreacion = ordenCreacion;
    }

    public String getNotaCreacion() {
        return notaCreacion;
    }

    public void setNotaCreacion(String notaCreacion) {
        this.notaCreacion = notaCreacion;
    }

    public String getNotaTipo() {
        return notaTipo;
    }

    public void setNotaTipo(String notaTipo) {
        this.notaTipo = notaTipo;
    }

    public BigDecimal getNotaFolio() {
        return notaFolio;
    }

    public void setNotaFolio(BigDecimal notaFolio) {
        this.notaFolio = notaFolio;
    }

    public BigDecimal getNotaTotal() {
        return notaTotal;
    }

    public void setNotaTotal(BigDecimal notaTotal) {
        this.notaTotal = DecimalPrecisionUtils.roundToTwoDecimals(notaTotal);
    }

    public BigDecimal getNotaMontoPendiente() {
        return notaMontoPendiente;
    }

    public void setNotaMontoPendiente(BigDecimal notaMontoPendiente) {
        this.notaMontoPendiente = DecimalPrecisionUtils.roundToTwoDecimals(notaMontoPendiente);
    }

    public BigDecimal getNotaMontoUsado() {
        return notaMontoUsado;
    }

    public void setNotaMontoUsado(BigDecimal notaMontoUsado) {
        this.notaMontoUsado = DecimalPrecisionUtils.roundToTwoDecimals(notaMontoUsado);
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getOrdenCodigo() {
        return ordenCodigo;
    }

    public void setOrdenCodigo(String ordenCodigo) {
        this.ordenCodigo = ordenCodigo;
    }

    public BigDecimal getOrdenNumero() {
        return ordenNumero;
    }

    public void setOrdenNumero(BigDecimal ordenNumero) {
        this.ordenNumero = ordenNumero;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    @Override
    public String toString() {
        return "ReportNotesDto [ordenCreacion=" + ordenCreacion + ", notaCreacion=" + notaCreacion + ", notaTipo=" + notaTipo +
               ", notaFolio=" + notaFolio + ", notaTotal=" + notaTotal + ", notaMontoPendiente=" + notaMontoPendiente +
               ", notaMontoUsado=" + notaMontoUsado + ", sucursal=" + sucursal + ", ordenCodigo=" + ordenCodigo +
               ", ordenNumero=" + ordenNumero + ", clienteId=" + clienteId + ", clienteNombre=" + clienteNombre + "]";
    }
}