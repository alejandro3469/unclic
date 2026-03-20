package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "EncabezadoSolTraspaso")
@Data
public class EncabezadoSolTraspaso {
	
	@EmbeddedId
	private EncabezadoSolTraspasoId id;
	
	@Column(name = "CRCD")
	private String crcd;
	
	@Column(name = "CRR")
	private BigDecimal crr;
	
	@Column(name = "TRDJ1")
	private Date trdj1;
	
	@Column(name = "DRQJ1")
	private Date drqj1;
	
	@Column(name = "AN8")
	private BigDecimal an8;
	
	@Column(name = "AN82")
	private BigDecimal an82;
	
	@Column(name = "TXA1")
	private String txa1;
	
	@Column(name = "TOTALPESO")
	private BigDecimal totalPeso;

	@Column(name = "SUBTOT")
	private BigDecimal subtot;
	
	@Column(name = "IVA")
	private BigDecimal iva;
	
	@Column(name = "TOTAL")
	private BigDecimal total;
	
	@Column(name = "DOCOREL")
	private BigDecimal docoRel;
	
	@Column(name = "DCTOREL")
	private String dctoRel;
	
	@Column(name = "ESTATUS")
	private String estatus;
	
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
	
	private String ModifiedBy;
	
	private Date ModifiedDate;
	
	private String ModifiedOn;
	
	

}
