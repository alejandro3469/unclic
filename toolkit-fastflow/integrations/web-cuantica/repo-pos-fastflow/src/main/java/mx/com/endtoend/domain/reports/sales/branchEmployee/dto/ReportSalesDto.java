package mx.com.endtoend.domain.reports.sales.branchEmployee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ReportSalesDto {
    private String ordenCreacion;
    private String sucursal;
    private Long usuarioNumero;
    private String empleadoVendedor;
    private String empleadoCobro;
    private String ordenCodigo;
    private BigDecimal ordenNumero;
    
    @NotNull(message = "El total de la orden es obligatorio")
    @DecimalMin(value = "0.0", message = "El total de la orden debe ser mayor o igual a 0")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal ordenTotal;
    
    private Long clienteId;
    private String clienteNombre;
    private String ordenCobro;
    
    @NotNull(message = "El cobro en efectivo es obligatorio")
    @DecimalMin(value = "0.0", message = "El cobro en efectivo debe ser mayor o igual a 0")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal cobroEfectivo;
    
    @NotNull(message = "El cobro por transferencia es obligatorio")
    @DecimalMin(value = "0.0", message = "El cobro por transferencia debe ser mayor o igual a 0")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal cobroTransferencia;
    
    @NotNull(message = "El cobro con tarjeta de crédito es obligatorio")
    @DecimalMin(value = "0.0", message = "El cobro con tarjeta de crédito debe ser mayor o igual a 0")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal cobroTarjetaCredito;
    
    @NotNull(message = "El cobro con nota de crédito es obligatorio")
    @DecimalMin(value = "0.0", message = "El cobro con nota de crédito debe ser mayor o igual a 0")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal cobroNotaCredito;
    
    @NotNull(message = "El pago a crédito es obligatorio")
    @DecimalMin(value = "0.0", message = "El pago a crédito debe ser mayor o igual a 0")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pagoCredito;

    // Constructor que coincide con los parámetros de la consulta
    public ReportSalesDto(String ordenCreacion, String sucursal, Long usuarioNumero, String empleadoVendedor, String empleadoCobro,
                          String ordenCodigo, BigDecimal ordenNumero, BigDecimal ordenTotal, Long clienteId, String clienteNombre,
                          String ordenCobro, BigDecimal cobroEfectivo, BigDecimal cobroTransferencia, BigDecimal cobroTarjetaCredito,
                          BigDecimal cobroNotaCredito, BigDecimal pagoCredito) {
        this.ordenCreacion = ordenCreacion;
        this.sucursal = sucursal;
        this.usuarioNumero = usuarioNumero;
        this.empleadoVendedor = empleadoVendedor;
        this.empleadoCobro = empleadoCobro;
        this.ordenCodigo = ordenCodigo;
        this.ordenNumero = ordenNumero;
        this.ordenTotal = ordenTotal != null ? DecimalPrecisionUtils.roundToTwoDecimals(ordenTotal) : BigDecimal.ZERO;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.ordenCobro = ordenCobro;
        this.cobroEfectivo = cobroEfectivo != null ? DecimalPrecisionUtils.roundToTwoDecimals(cobroEfectivo) : BigDecimal.ZERO;
        this.cobroTransferencia = cobroTransferencia != null ? DecimalPrecisionUtils.roundToTwoDecimals(cobroTransferencia) : BigDecimal.ZERO;
        this.cobroTarjetaCredito = cobroTarjetaCredito != null ? DecimalPrecisionUtils.roundToTwoDecimals(cobroTarjetaCredito) : BigDecimal.ZERO;
        this.cobroNotaCredito = cobroNotaCredito != null ? DecimalPrecisionUtils.roundToTwoDecimals(cobroNotaCredito) : BigDecimal.ZERO;
        this.pagoCredito = pagoCredito != null ? DecimalPrecisionUtils.roundToTwoDecimals(pagoCredito) : BigDecimal.ZERO;
    }

    public String getOrdenCreacion() {
        return ordenCreacion;
    }

    public void setOrdenCreacion(String ordenCreacion) {
        this.ordenCreacion = ordenCreacion;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public Long getUsuarioNumero() {
        return usuarioNumero;
    }

    public void setUsuarioNumero(Long usuarioNumero) {
        this.usuarioNumero = usuarioNumero;
    }

    public String getEmpleadoVendedor() {
        return empleadoVendedor;
    }

    public void setEmpleadoVendedor(String empleadoVendedor) {
        this.empleadoVendedor = empleadoVendedor;
    }

    public String getEmpleadoCobro() {
        return empleadoCobro;
    }

    public void setEmpleadoCobro(String empleadoCobro) {
        this.empleadoCobro = empleadoCobro;
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

    public BigDecimal getOrdenTotal() {
        return ordenTotal;
    }

    public void setOrdenTotal(BigDecimal ordenTotal) {
        this.ordenTotal = DecimalPrecisionUtils.roundToTwoDecimals(ordenTotal);
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

    public String getOrdenCobro() {
        return ordenCobro;
    }

    public void setOrdenCobro(String ordenCobro) {
        this.ordenCobro = ordenCobro;
    }

    public BigDecimal getCobroEfectivo() {
        return cobroEfectivo;
    }

    public void setCobroEfectivo(BigDecimal cobroEfectivo) {
        this.cobroEfectivo = DecimalPrecisionUtils.roundToTwoDecimals(cobroEfectivo);
    }

    public BigDecimal getCobroTransferencia() {
        return cobroTransferencia;
    }

    public void setCobroTransferencia(BigDecimal cobroTransferencia) {
        this.cobroTransferencia = DecimalPrecisionUtils.roundToTwoDecimals(cobroTransferencia);
    }

    public BigDecimal getCobroTarjetaCredito() {
        return cobroTarjetaCredito;
    }

    public void setCobroTarjetaCredito(BigDecimal cobroTarjetaCredito) {
        this.cobroTarjetaCredito = DecimalPrecisionUtils.roundToTwoDecimals(cobroTarjetaCredito);
    }

    public BigDecimal getCobroNotaCredito() {
        return cobroNotaCredito;
    }

    public void setCobroNotaCredito(BigDecimal cobroNotaCredito) {
        this.cobroNotaCredito = DecimalPrecisionUtils.roundToTwoDecimals(cobroNotaCredito);
    }

    public BigDecimal getPagoCredito() {
        return pagoCredito;
    }

    public void setPagoCredito(BigDecimal pagoCredito) {
        this.pagoCredito = DecimalPrecisionUtils.roundToTwoDecimals(pagoCredito);
    }

    @Override
    public String toString() {
        return "ReportSalesDto [ordenCreacion=" + ordenCreacion + ", sucursal=" + sucursal + ", usuarioNumero=" + usuarioNumero + ", empleadoVendedor=" + empleadoVendedor + ", empleadoCobro=" + empleadoCobro + ", ordenCodigo=" + ordenCodigo + ", ordenNumero=" + ordenNumero + ", ordenTotal=" + ordenTotal + ", clienteId=" + clienteId + ", clienteNombre=" + clienteNombre + ", ordenCobro=" + ordenCobro + ", cobroEfectivo=" + cobroEfectivo + ", cobroTransferencia=" + cobroTransferencia + ", cobroTarjetaCredito=" + cobroTarjetaCredito + ", cobroNotaCredito=" + cobroNotaCredito + ", pagoCredito=" + pagoCredito + "]";
    }
}