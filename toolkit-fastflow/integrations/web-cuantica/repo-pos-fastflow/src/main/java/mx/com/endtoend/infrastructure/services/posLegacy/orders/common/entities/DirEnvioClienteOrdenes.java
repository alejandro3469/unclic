package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "DirEnvioClienteOrdenes")
@Data
public class DirEnvioClienteOrdenes {

	@EmbeddedId
	private DirEnvioClienteOrdenesId id;

	@Column(name = "Calle")
	private String calle;

	@Column(name = "NoExterior")
	private String noExterior;

	@Column(name = "NoInterior")
	private String noInterior;

	@Column(name = "CodigoPostal")
	private String codigoPostal;

	@Column(name = "Ciudad")
	private String ciudad;

	@Column(name = "Colonia")
	private String colonia;

	@Column(name = "Estado")
	private String estado;

	@Column(name = "DesEstado")
	private String desEstado;

	@Column(name = "Delegacion")
	private String delegacion;

	@Column(name = "DesDelegacion")
	private String desDelegacion;

	@Column(name = "Plano")
	private String plano;

	@Column(name = "DesPlano")
	private String desPlano;

	@Column(name = "Coordenada")
	private String coordenada;

	@Column(name = "DesCoordenada")
	private String desCoordenada;

	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Column(name = "ModifiedDate")
	private Date modifiedDate;

	@Column(name = "ModifiedOn")
	private String modifiedOn;

}
