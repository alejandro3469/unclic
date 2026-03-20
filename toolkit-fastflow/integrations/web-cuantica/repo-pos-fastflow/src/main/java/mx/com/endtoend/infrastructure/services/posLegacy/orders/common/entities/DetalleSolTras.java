package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "DetalleSolTras")
@Data
public class DetalleSolTras {
	
	@EmbeddedId
	private DetalleSolTrasId id;
	
	@Column(name = "TIPOLINEA")
	private String tipoLinea;
	
	@Column(name = "ITM")
	private BigDecimal itm;
	
	@Column(name = "DESC1")
	private String desc1;
	
	@Column(name = "UM")
	private String um;
	
	@Column(name = "QTY")
	private BigDecimal qty;
	
	@Column(name = "QTRY")
	private BigDecimal qtry;
	
	@Column(name = "PESO")
	private BigDecimal peso;
	
	@Column(name = "PRECIO")
	private BigDecimal precio;
	
	@Column(name = "PRECIOTOT")
	private BigDecimal precioTot;
	
	@Column(name = "CRCD")
	private String crcd;
	
	@Column(name = "TIPOCAMBIO")
	private BigDecimal tipoCambio;
	
	@Column(name = "DOCOORIGEN")
	private BigDecimal docoOrigen;
	
	@Column(name = "DCTOORIGEN")
	private String dctoOrigen;
	
	@Column(name = "NXTR")
	private String nxtr;
	
	@Column(name = "LTTR")
	private String lttr;
	
	@Column(name = "NXTRPOS")
	private String nxtrPos;
	
	@Column(name = "LTTRPOS")
	private String lttrPos;
	
	@Column(name = "FACTORPESO")
	private BigDecimal factorPeso;
	
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
	
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	
	@Column(name = "ModifiedDate")
	private Date modifiedDate;
	
	@Column(name = "ModifiedOn")
	private String modifiedOn;

}
