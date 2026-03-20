package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tblEncabezadoOrden")
@Data
public class TblEncabezadoOrden {

	@EmbeddedId
	private TblEncabezadoOrdenId id;
	
	@Column(name = "MCU")
	private String mcu;
	
	@Column(name = "CRCD")
	private String crcd;
	
	@Column(name = "CRR")
	private BigDecimal crr;
	
	@Column(name = "ASN")
	private String asn;
	
	@Column(name = "TRAR")
	private String trar;
	
	@Column(name = "TRDJ1")
	private Date trdj1;
	
	@Column(name = "DRQJ1")
	private Date drqj1;
	
	@Column(name = "AN8")
	private BigDecimal an8;
	
	@Column(name = "AN82")
	private BigDecimal an82;
	
	@Column(name = "VR01")
	private String vr01;
	
	@Column(name = "ZON")
	private String zon;
	
	@Column(name = "STOP")
	private String stop;
	
	@Column(name = "TXA1")
	private String txa1;
	
	@Column(name = "TOTALPESO")
	private BigDecimal totalPeso;
	
	@Column(name = "SUBTOT")
	private BigDecimal subTot;
	
	@Column(name = "IVA")
	private BigDecimal iva;
	
	@Column(name = "TOTAL")
	private BigDecimal total;
	
	@Column(name = "DOCOORIGEN")
	private BigDecimal docoOrigen;
	
	@Column(name = "DCTOORIGEN")
	private String dctoOrigen;
	
	@Column(name = "IMPORTEPENDIENTE")
	private BigDecimal importePendiente;
	
	@Column(name = "PORCENCABEZADO")
	private BigDecimal porcenEncabezado;
	
	@Column(name = "AN8AUTORIZADO")
	private BigDecimal an8Autorizado;
	
	@Column(name = "ESTATUS")
	private int estatus;
	
	@Column(name = "RETENIDO")
	private String retenido;
	
	@Column(name = "PORCVEND")
	private BigDecimal porcVend;
	
	@Column(name = "PORCGTETIENDA")
	private BigDecimal porcGteTienda;
	
	@Column(name = "PORCGTEZONA")
	private BigDecimal porcGteZona;
	
	@Column(name = "MCUSOLICITANTE")
	private String mcuSolicitante;
	
	@Column(name = "CAMPO1")
	private String campo1;
	
	@Column(name = "CAMPO2")
	private String campo2;
	
	@Column(name = "CAMPO3")
	private String campo3;
	
	@Column(name = "CAMPO4")
	private String campo4;
	
	@Column(name = "CAMPO5")
	private BigDecimal campo5;
	
	@Column(name = "CAMPO6")
	private BigDecimal campo6;
	
	@Column(name = "CAMPO7")
	private BigDecimal campo7;
	
	@Column(name = "CAMPO8")
	private BigDecimal campo8;
	
	@Column(name = "CAMPO9")
	private Date campo9;
	
	@Column(name = "CAMPO10")
	private Date campo10;
	
	@Column(name = "CAMPO11")
	private Date campo11;
	
	@Column(name = "CAMPO12")
	private Date campo12;
	
	@Column(name = "DOCOJDE")
	private BigDecimal docoJde;
	
	@Column(name = "DCTOJDE")
	private String dctoJde;
	
	@Column(name = "EstatusMigrNodoCentral")
	private int estatusMigrNodoCentral;
	
	@Column(name = "EstatusMigrJDE")
	private int estatusMigrJDE;
	
	@Column(name = "BLOQUEADO")
	private boolean bloqueado;
	
	@Column(name = "NOTASCREDITO")
	private String notasCredito;
	
	@Column(name = "OBSERVACIONES")
	private String observaciones;
	
	@Column(name = "IMPRESION")
	private int impresion;
	
	@Column(name = "PORCMAXAUT")	
	private BigDecimal porcMaxAut;
	
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	
	@Column(name = "ModifiedDate")
	private Date modifiedDate;
	
	@Column(name = "ModifiedOn")
	private String modifiedOn;
	
	@Column(name = "CANCELADO")
	private boolean cancelado;
	
	@Column(name = "FacturaTope")
	private boolean facturaTope;
	
	@Column(name = "MigJDEInt")
	private int migJDEInt;
	
	@Column(name = "FacturaTopeMonto")
	private BigDecimal facturaTopeMonto;
	
	
	
	
}
