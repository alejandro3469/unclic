package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Usuarios")
@Data
public class Usuarios {

	@Id
	private String IdUsuario;
	
	private String Nombre;
	
	private String Password;
	
	private String Email;
	
	private Date FechaLimite;
	
	private boolean Estatus;
	
	private String Compania;
	
	private String TipoCliente;
	
	private String CodCondicionesPago;
	
	private double An8;
	
	private double PorDescuento;
	
	private String GerenteTienda;
	
	private String GerenteZona;
	
	private String ModifiedBy;
	
	private Date ModifiedDate;
	
	private String ModifiedOn;
	
	private String IdCaja;
	
	private String SubGerente;
	
	private String RFC;
}
